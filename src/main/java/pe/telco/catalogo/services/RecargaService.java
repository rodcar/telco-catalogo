package pe.telco.catalogo.services;

import java.util.List;

import pe.telco.catalogo.messages.requests.RecargaCreateRequest;
import pe.telco.catalogo.messages.responses.RecargaHistorialResponse;

public interface RecargaService {
	void registerRecarga(RecargaCreateRequest request);
	
	List<RecargaHistorialResponse> fetchHistorialByUserId(Long id);
}
