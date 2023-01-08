package pe.telco.catalogo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import pe.telco.catalogo.messages.responses.CatalogoItem;

@Service
public class CatalogoServiceImpl implements CatalogoService {

	@Override
	public List<CatalogoItem> fetchAllItems() throws Exception {
		List<CatalogoItem> items = new ArrayList<>();
		return items;
	}

}