package br.com.sicredi.api.exception.error;

public record ErrorParameter(
       String message,
       String field,
       Object value
) {
}
