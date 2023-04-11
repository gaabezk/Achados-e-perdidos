package br.com.gabezk.achadoseperdidos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Achados&Perdidos - Api",
                version = "1.0",
                description = "SpringBoot Web Api",
                contact = @Contact(
                        name = "Gabriel Fernandes",
                        email = "gabriel1521@outlook.com",
                        url = "https://github.com/gaabezk"
                )
        )
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class AchadosEPerdidosApplication {
    public static void main(String[] args) {
        SpringApplication.run(AchadosEPerdidosApplication.class, args);
    }
}