package br.com.sicredi.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RulingResponse {

    @Schema(description = "Identity of the ruling", example = "507f1f77bcf86cd799439011")
    private String id;
    @Schema(description = "Rulings name", example = "Name of ruling")
    private String name;
}
