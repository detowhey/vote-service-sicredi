package br.com.sicredi.api.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

public class OpenApiConfig {

    @Bean
    public OpenAPI openApiConfig() {
        return new OpenAPI()
                .info(new Info().title("Sicredi voting API")
                        .description("Application to count the votes of the rulings")
                        .version("v1"));

    }

    @Bean
    public OpenApiCustomiser customGlobalResponses() {
        return openApi ->
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                ApiResponses apiResponses = operation.getResponses();

                ApiResponse apiResponseInternalServerError = new ApiResponse().description("Internal server error")
                        .content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE, new io.swagger.v3.oas.models.media.MediaType()));

                ApiResponse apiResponseNotFound = new ApiResponse().description("The ruling you are trying to access was not found")
                        .content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE, new io.swagger.v3.oas.models.media.MediaType()));

                apiResponses.addApiResponse("500", apiResponseInternalServerError);
                apiResponses.addApiResponse("404", apiResponseNotFound);
            }));
    }
}
