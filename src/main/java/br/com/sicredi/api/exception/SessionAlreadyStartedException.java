package br.com.sicredi.api.exception;

public class SessionAlreadyStartedException extends RuntimeException{
    public SessionAlreadyStartedException(String rulingId) {
        super(String.format("The ruling session '%s' has already been started previously.", rulingId));
    }
}
