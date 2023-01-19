package pe.telco.catalogo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Block;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import pe.telco.catalogo.messages.requests.RecargaCreateRequest;
import pe.telco.catalogo.messages.responses.RecargaHistorialResponse;
import reactor.core.publisher.Mono;

@Service
public class RecargaServiceImpl implements RecargaService {

	@Autowired
	private WebClient.Builder webClientBuilder;

	private final ReactiveCircuitBreaker recargaCircuitBreaker;

	public RecargaServiceImpl(ReactiveCircuitBreakerFactory circuitBreakerFactory) {
		this.recargaCircuitBreaker = circuitBreakerFactory.create("recargas");
	}

	@Override
	public void registerRecarga(RecargaCreateRequest request) {
		try {
			recargaCircuitBreaker.run(webClientBuilder.build().post().uri("http://recargas/recargas")
					.contentType(MediaType.APPLICATION_JSON).bodyValue(request).retrieve().bodyToMono(String.class))
					.block();
		} catch (Exception e) {
			recargaCircuitBreaker.run(webClientBuilder.build().post().uri("http://recargas-respaldo/recargas")
					.contentType(MediaType.APPLICATION_JSON).bodyValue(request).retrieve().bodyToMono(String.class))
					.block();
		}
	}

	@Override
	public List<RecargaHistorialResponse> fetchHistorialByUserId(Long id) {
		try {
			Mono<List<RecargaHistorialResponse>> historialResponse = recargaCircuitBreaker
					.run(webClientBuilder.build().get().uri("http://recargas/recargas/historial/" + id).retrieve()
							.bodyToMono(new ParameterizedTypeReference<List<RecargaHistorialResponse>>() {
							}));

			List<RecargaHistorialResponse> historial = historialResponse.block();
			return historial;
		} catch (Exception e) {
			Mono<List<RecargaHistorialResponse>> historialRespaldoResponse = recargaCircuitBreaker
					.run(webClientBuilder.build().get().uri("http://recargas-respaldo/recargas/historial/" + id)
							.retrieve().bodyToMono(new ParameterizedTypeReference<List<RecargaHistorialResponse>>() {
							}));

			List<RecargaHistorialResponse> historialRespaldo = historialRespaldoResponse.block();
			return historialRespaldo;
		}
	}

}
