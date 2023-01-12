package pe.telco.catalogo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import pe.telco.catalogo.messages.responses.CatalogoItem;
import pe.telco.catalogo.messages.responses.PaqueteInternetResponse;
import pe.telco.catalogo.messages.responses.PaqueteResponse;
import reactor.core.publisher.Mono;

@Service
public class CatalogoServiceImpl implements CatalogoService {

	@Autowired
	private WebClient.Builder webClientBuilder;

	private final ReactiveCircuitBreaker catalogoCircuitBreaker;

	public CatalogoServiceImpl(ReactiveCircuitBreakerFactory circuitBreakerFactory) {
		this.catalogoCircuitBreaker = circuitBreakerFactory.create("recommended");
	}

	@Override
	public List<CatalogoItem> fetchAllItems() throws Exception {
		List<CatalogoItem> items = new ArrayList<>();

		Mono<List<PaqueteResponse>> response = webClientBuilder.build().get().uri("http://paquetes/paquetes").retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<PaqueteResponse>>() {
				});

		List<PaqueteResponse> paquetes = response.block();

		for (PaqueteResponse paquete : paquetes) {
			items.add(CatalogoItem.builder().id(paquete.getId().toString()).nombre(paquete.getNombre())
					.descripcion(paquete.getDescripcion()).precio(paquete.getPrecio()).categoria("redes").build());
		}

		Mono<List<PaqueteInternetResponse>> internetPaqueteResponse = webClientBuilder.build().get()
				.uri("http://paquetesinternet/paquetesinternet").retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<PaqueteInternetResponse>>() {
				});

		List<PaqueteInternetResponse> paquetesInternet = internetPaqueteResponse.block();
		for (PaqueteInternetResponse paquete : paquetesInternet) {
			items.add(CatalogoItem.builder().id(paquete.getId()).nombre(paquete.getNombre())
					.descripcion(paquete.getDescripcion()).precio(paquete.getPrecio()).categoria("internet").build());
		}

		return items;
	}

	@Override
	public List<CatalogoItem> fetchByCategoria(String categoria) throws Exception {
		List<CatalogoItem> items = new ArrayList<>();

		switch (categoria) {
		case "redes":
			Mono<List<PaqueteResponse>> redSocialPaqueteResponse = catalogoCircuitBreaker
					.run(webClientBuilder.build().get().uri("http://paquetes/paquetes").retrieve()
							.bodyToMono(new ParameterizedTypeReference<List<PaqueteResponse>>() {
							}), throwable -> {
								List<PaqueteResponse> predeterminada = new ArrayList<>();
								// TODO Agregar paquetes predeterminados
								// TODO Retornar una Excepción personaliza
								return Mono.just(predeterminada);
							});

			List<PaqueteResponse> paquetes = redSocialPaqueteResponse.block();

			for (PaqueteResponse paquete : paquetes) {
				items.add(CatalogoItem.builder().id(paquete.getId().toString()).nombre(paquete.getNombre())
						.descripcion(paquete.getDescripcion()).precio(paquete.getPrecio()).categoria("redes").build());
			}
			break;
		case "internet":
			Mono<List<PaqueteInternetResponse>> internetPaqueteResponse = webClientBuilder.build().get()
					.uri("http://paquetesinternet/paquetesinternet").retrieve()
					.bodyToMono(new ParameterizedTypeReference<List<PaqueteInternetResponse>>() {
					});

			List<PaqueteInternetResponse> paquetesInternet = internetPaqueteResponse.block();
			for (PaqueteInternetResponse paquete : paquetesInternet) {
				items.add(CatalogoItem.builder().id(paquete.getId()).nombre(paquete.getNombre())
						.descripcion(paquete.getDescripcion()).precio(paquete.getPrecio()).categoria("internet")
						.build());
			}
			break;
		default:
			// TODO throw NotFound exception
		}

		return items;
	}

}