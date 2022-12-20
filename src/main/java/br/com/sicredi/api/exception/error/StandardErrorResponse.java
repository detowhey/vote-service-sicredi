package br.com.sicredi.api.exception.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class StandardErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "UTC")
    private Instant timestamp;
    private Integer statusCode;
    private String error;
    private String message;
    private String path;
}
