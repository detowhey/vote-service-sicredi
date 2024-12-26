package br.com.sicredi.api.exception.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

public record StandardErrorResponse(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy - HH:mm:ss", timezone = "America/Sao_Paulo")
        Instant timestamp,
        Integer statusCode,
        String error,
        String message,
        String path
) {
}
