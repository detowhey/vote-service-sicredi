package br.com.sicredi.api.exception;

public class CpfMemberRegistredException extends RuntimeException {

    CpfMemberRegistredException(String message) {
        super(message);
    }
}
