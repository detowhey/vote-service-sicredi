package br.com.sicredi.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RulingRequest {

    @Schema(description = "Ruling's name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Ruling name")
    @Size(min = 5, message = "Name is more then 5 chars")
    @NotBlank(message = "Name cannot be empty")
    private String name;
}
