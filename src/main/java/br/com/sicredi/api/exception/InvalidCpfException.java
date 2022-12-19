package br.com.sicredi.api.exception;

public class InvalidCpfException extends RuntimeException {

    public InvalidCpfException(String cpf) {
        super(String.format("The cpf '%s' is invalid", cpf));
    }
}
