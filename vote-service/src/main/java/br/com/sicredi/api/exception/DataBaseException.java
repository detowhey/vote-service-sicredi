package br.com.sicredi.api.exception;


public class DataBaseException extends RuntimeException {

    public DataBaseException() {
        super("Could not connect to the database");
    }
}
