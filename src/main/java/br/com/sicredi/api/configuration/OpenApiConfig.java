package br.com.sicredi.api.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info().title("Sicredi voting API")
                        .description("Application to count the votes of the rulings")
                        .version(appVersion))
                .servers(List.of(new Server().url("http://localhost:8082").description("URL service")));

    }
    @Bean
    public OpenApiCustomiser customGlobalResponses() {
        return openApi ->
                openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                    ApiResponse globalResponseInternalServerError = new ApiResponse().description("Internal server error");
                    ApiResponse globalResponseNotFound = new ApiResponse().description("The ruling you are trying to access was not found");

                    ApiResponses apiResponses = operation.getResponses();
                    apiResponses.addApiResponse("500", globalResponseInternalServerError);
                    apiResponses.addApiResponse("404", globalResponseNotFound);
                }));
    }
}
