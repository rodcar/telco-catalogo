package pe.telco.catalogo.messages.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatalogoItem {
	private String id;
	private String nombre;
	private String descripcion;
	private String imagen;
	private Double precio;
	private String categoria;
}
