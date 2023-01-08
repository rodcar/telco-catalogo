package pe.telco.catalogo.services;

import java.util.List;

import pe.telco.catalogo.messages.responses.CatalogoItem;

public interface CatalogoService {
	List<CatalogoItem> fetchAllItems() throws Exception;
}