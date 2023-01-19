package pe.telco.catalogo.messages.responses;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecargaHistorialResponse {
	private String numero;
	private Double monto;
	private Date fecha;
}
