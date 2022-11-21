package br.com.sicredi.api.exception;

public class ObjectNotFoundException extends RuntimeException {

    ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException() {
        super("Object not found");
    }
}
