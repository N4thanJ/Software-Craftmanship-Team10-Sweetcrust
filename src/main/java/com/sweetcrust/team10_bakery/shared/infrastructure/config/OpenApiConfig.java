package com.sweetcrust.team10_bakery.shared.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI sweetCrustOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("support@sweetcrust.com");
        contact.setName("SweetCrust Support");

        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Local dev server");

        Info info = new Info()
                .title("SweetCrust API")
                .version("1.0.0")
                .contact(contact)
                .description("API for the SweetCrust bakery chain");

        return new OpenAPI().info(info).servers(List.of(server));
    }
}
