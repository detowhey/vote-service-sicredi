package br.com.sicredi.api.exception;


public class DataBaseException extends RuntimeException {

    DataBaseException(String message) {
        super(message);
    }
}
