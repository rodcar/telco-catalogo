package pe.telco.catalogo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.telco.catalogo.messages.responses.RecargaHistorialResponse;
import pe.telco.catalogo.services.RecargaService;

@RestController
@RequestMapping("/recargas2")
public class RecargaController {

	@Autowired
	private RecargaService recargaService;
	
	@GetMapping(value = "/historial/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
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
}
