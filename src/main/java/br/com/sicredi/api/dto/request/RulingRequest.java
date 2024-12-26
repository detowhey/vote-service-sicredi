package br.com.sicredi.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record RulingRequest(
        @Schema(description = "Ruling's name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Ruling name")
        @NotBlank(message = "Name cannot be empty")
        @Size(min = 5, message = "Name is more then 5 chars")
        String name
) {
}
