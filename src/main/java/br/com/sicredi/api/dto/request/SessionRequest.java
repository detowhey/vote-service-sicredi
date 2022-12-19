package br.com.sicredi.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequest {

    @Schema(description = "Session duration in minutes", example = "2")
    @Min(value = 1, message = "Session time must be at least 1 minute")
    @NotNull(message = "Minutes not be null")
    private Integer minutes;
}
