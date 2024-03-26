package org.lafabrique_epita.exposition.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${openapi.dev-url}")
    private String openApiDevUrl;

    @Value("${openapi.prod-url}")
    private String openApiProdUrl;

    @Bean
    OpenAPI customOpenApi() {
        Server devServer = new Server();
        devServer.setUrl(openApiDevUrl);
        devServer.setDescription("Development server");

        Server prodServer = new Server();
        prodServer.setUrl(openApiProdUrl);
        prodServer.setDescription("Production server");


        return new OpenAPI()
                .info(new Info()
                        .title("La Fabrique Epita API")
                        .version("1.0")
                        .contact(new Contact()
                                .email("fatiha.benou-halima@epita.fr")
                                .name("Fatiha Benou-halima"))
                )
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(
                        new Components()
                                .addSecuritySchemes("Bearer Authentication",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")

                                ))
                .servers(List.of(devServer, prodServer));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/**")
                .build();
    }
}
