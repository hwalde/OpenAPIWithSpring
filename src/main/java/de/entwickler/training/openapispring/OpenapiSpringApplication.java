package de.entwickler.training.openapispring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "de.entwickler.training.openapispring", // Ihr eigener Code inkl. Delegate
        "org.openapitools.api",                // Generierte API-Controller
        "org.openapitools.configuration"       // Generierte OpenAPI/Swagger Konfiguration
})
public class OpenapiSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenapiSpringApplication.class, args);
    }

}
