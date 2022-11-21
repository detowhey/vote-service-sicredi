package br.com.sicredi.api.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String id) {
        super("Resource not found. Id " + id);
    }
}
