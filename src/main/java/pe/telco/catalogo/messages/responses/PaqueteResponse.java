package pe.telco.catalogo.messages.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaqueteResponse {
	private Long id;
	private String nombre;
	private String descripcion;
	private String imagen;
	private Double precio;
}
