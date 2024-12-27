package br.com.sicredi.api.configuration.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${api.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info().title("Sicredi voting API")
                        .description("Application to count the votes of the rulings")
                        .version(appVersion));
    }

    @Bean
    public GroupedOpenApi customOpenApi() {
        ApiResponse response500 = new ApiResponse().description(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        ApiResponse response400 = new ApiResponse().description(HttpStatus.BAD_REQUEST.getReasonPhrase());
        ApiResponse response401 = new ApiResponse().description(HttpStatus.CONFLICT.getReasonPhrase());
        ApiResponse response404 = new ApiResponse().description(HttpStatus.NOT_FOUND.getReasonPhrase());
        ApiResponse response403 = new ApiResponse().description(HttpStatus.FORBIDDEN.getReasonPhrase());

        return GroupedOpenApi.builder()
                .group("api")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    operation.getResponses()
                            .addApiResponse(
                                    String.valueOf(HttpStatus.BAD_REQUEST.value()),
                                    response400)
                            .addApiResponse(
                                    String.valueOf(HttpStatus.NOT_FOUND.value()),
                                    response404)
                            .addApiResponse(String.valueOf(HttpStatus.CONFLICT.value()),
                                    response401)
                            .addApiResponse(
                                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                                    response500)
                            .addApiResponse(String.valueOf(HttpStatus.FORBIDDEN.value()),
                                    response403);

                    return operation;
                })
                .build();
    }
}
