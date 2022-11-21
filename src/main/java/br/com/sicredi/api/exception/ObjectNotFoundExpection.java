package br.com.sicredi.api.exception;

public class ObjectNotFoundExpection extends RuntimeException {

    ObjectNotFoundExpection(String message) {
        super(message);
    }

    public ObjectNotFoundExpection() {
        super("Object not found");
    }
}
