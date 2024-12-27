package br.com.sicredi.api.exception.error;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.List;

public record ErrorParameterResponse(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy - HH:mm:ss", timezone = "America/Sao_Paulo")
        Instant timestamp,
        Integer status,
        String message,
        List<ErrorParameter> errors
) {
}
