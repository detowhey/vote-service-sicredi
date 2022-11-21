package br.com.sicredi.api.exception;

public class ResourceNotFoundExpection extends RuntimeException {

    public ResourceNotFoundExpection(long id) {
        super("Resource not found. Id " + id);
    }
}
