package pe.telco.catalogo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import pe.telco.catalogo.messages.responses.CatalogoItem;
import pe.telco.catalogo.messages.responses.PaqueteResponse;
import reactor.core.publisher.Mono;

@Service
public class CatalogoServiceImpl implements CatalogoService {

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Override
	public List<CatalogoItem> fetchAllItems() throws Exception {
		List<CatalogoItem> items = new ArrayList<>();

		Mono<List<PaqueteResponse>> response = webClientBuilder.build().get().uri("http://paquetes/paquetes").retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<PaqueteResponse>>() {
				});

		List<PaqueteResponse> paquetes = response.block();

		for (PaqueteResponse paquete : paquetes) {
			items.add(CatalogoItem.builder().id(paquete.getId().toString()).nombre(paquete.getNombre())
					.descripcion(paquete.getDescripcion()).precio(paquete.getPrecio()).build());
		}

		return items;
	}

}