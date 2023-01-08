package pe.telco.catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TelcoCatalogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelcoCatalogoApplication.class, args);
	}

}
