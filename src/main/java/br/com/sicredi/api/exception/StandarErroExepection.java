package br.com.sicredi.api.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class StandarErroExepection {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-/MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant timestamp;
    private Integer statusCode;
    private String error;
    private String message;
    private String path;
}
