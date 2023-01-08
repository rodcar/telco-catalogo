package pe.telco.catalogo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.telco.catalogo.messages.responses.CatalogoItem;
import pe.telco.catalogo.services.CatalogoService;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {

	@Autowired
	private CatalogoService catalogoService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CatalogoItem>> fetchAllItems() {
		try {
			List<CatalogoItem> items = catalogoService.fetchAllItems();
			return (items.isEmpty()) ? ResponseEntity.noContent().build() : ResponseEntity.ok(items);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
}
