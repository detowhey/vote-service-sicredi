package br.com.sicredi.api.exception;

public class RulingNotFoundException extends RuntimeException {

    public RulingNotFoundException(String rulingId) {
        super("Ruling not found with this id '%s'".formatted(rulingId));
    }
}
