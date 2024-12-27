package br.com.sicredi.api.exception;

public class SessionAlreadyStartedException extends RuntimeException {

    public SessionAlreadyStartedException(String rulingId) {
        super("The ruling session '%s' has already been started previously.".formatted(rulingId));
    }
}
