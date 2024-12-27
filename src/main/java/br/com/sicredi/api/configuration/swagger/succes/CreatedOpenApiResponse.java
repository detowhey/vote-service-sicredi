package br.com.sicredi.api.configuration.swagger.succes;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ApiResponse(responseCode = "201", description = "Created")
public @interface CreatedOpenApiResponse {
}
