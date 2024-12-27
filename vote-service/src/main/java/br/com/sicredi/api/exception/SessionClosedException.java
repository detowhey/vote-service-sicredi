package br.com.sicredi.api.exception;

public class SessionClosedException extends RuntimeException {

    public SessionClosedException() {
        super("The voting session for this ruling has now closed.");
    }
}
