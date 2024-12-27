package br.com.sicredi.api.exception;

public class InvalidCpfException extends RuntimeException {

    public InvalidCpfException(String cpf) {
        super("The cpf '%s' is invalid".formatted(cpf));
    }
}
