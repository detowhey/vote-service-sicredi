package br.com.sicredi.api.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class OpenApiConfig {

    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI()
                .info(new Info().title("Sicredi voting API")
                        .description("Application to count the votes of the rulings")
                        .version("v1"));
    }
}
