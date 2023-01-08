package pe.telco.catalogo.messages.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogoItem {
	private String id;
	private String nombre;
	private String descripcion;
	private Double precio;
}
