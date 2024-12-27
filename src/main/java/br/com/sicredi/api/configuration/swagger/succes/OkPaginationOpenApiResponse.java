package br.com.sicredi.api.configuration.swagger.succes;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ApiResponse(responseCode = "200", description = "Ok")
@Parameters(value = {
        @Parameter(name = "page", description = "Page number"),
        @Parameter(name = "size", description = "Item size"),
        @Parameter(name = "sortedBy", description = "Sort attribute"),
        @Parameter(name = "orderBy", description = "Ordering", example = "asc/desc")
})
public @interface OkPaginationOpenApiResponse {
}
