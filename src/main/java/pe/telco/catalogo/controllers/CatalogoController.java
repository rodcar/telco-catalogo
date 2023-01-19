package pe.telco.catalogo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.telco.catalogo.messages.requests.RecargaCreateRequest;
import pe.telco.catalogo.messages.responses.CatalogoItem;
import pe.telco.catalogo.messages.responses.RecargaHistorialResponse;
import pe.telco.catalogo.services.CatalogoService;
import pe.telco.catalogo.services.RecargaService;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private RecargaService recargaService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CatalogoItem>> fetchAllItems(@RequestParam(required = false) String categoria) {
		try {
			List<CatalogoItem> items;
			if (categoria != null) {
				items = catalogoService.fetchByCategoria(categoria);
			} else {
				items = catalogoService.fetchAllItems();
			}
			return (items.isEmpty()) ? ResponseEntity.noContent().build() : ResponseEntity.ok(items);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping(value = "/recargas/historial/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RecargaHistorialResponse>> fetchById(@PathVariable("userId") Long userId) {

		try {
			List<RecargaHistorialResponse> recargasHistorial = recargaService.fetchHistorialByUserId(userId);

			if (recargasHistorial.isEmpty()) {
				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.ok(recargasHistorial);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping(value = "/recargas", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(@Validated @RequestBody RecargaCreateRequest request) {
		try {
			recargaService.registerRecarga(request);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
}
