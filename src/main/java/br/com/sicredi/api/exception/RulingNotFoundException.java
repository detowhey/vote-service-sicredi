package br.com.sicredi.api.exception;

import org.springframework.http.HttpStatus;

public class RulingNotFoundException extends RuntimeException {

    public RulingNotFoundException(String rulingId) {
        super(String.format("Ruling not found with this id '%s'", rulingId));
    }
}
