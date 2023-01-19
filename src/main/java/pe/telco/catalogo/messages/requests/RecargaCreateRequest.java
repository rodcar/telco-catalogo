package pe.telco.catalogo.messages.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RecargaCreateRequest {
	@NotNull
	private Long userId;
	@Pattern(regexp = "^[0-9]+$", message = "el número de celular solo debe contener digitos")
	@Size(min = 9, max = 9)
	private String numero;
	@Min(3)
	private Double monto;
}
