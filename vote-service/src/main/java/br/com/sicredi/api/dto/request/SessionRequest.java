package br.com.sicredi.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public record SessionRequest(
        @Schema(description = "Session duration in minutes", example = "1")
        @NotNull(message = "Minutes not be null")
        @Min(value = 1, message = "Session time must be at least 1 minute")
        Integer minutes
) {
}
