package com.example.app;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}
	@Bean
	public OpenAPI customAPI(){
		return new OpenAPI().info(new Info()
				.version("1.0.36")
				.title("API AGENCIA")
				.description("Una agencia de turismo desea llevar a cabo el desarrollo de una aplicación que le permita" +
						" recibir solicitudes de reservas para los diferentes tipos de paquetes que ofrece. Por el momento " +
							"los dos servicios con los que cuenta son el de búsqueda y reserva de hoteles y búsqueda y reserva de vuelos."));
	}

}
