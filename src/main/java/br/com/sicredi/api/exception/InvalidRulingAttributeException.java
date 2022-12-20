package br.com.sicredi.api.exception;

public class InvalidRulingAttributeException extends RuntimeException {

    public InvalidRulingAttributeException(String attributeValue) {
        super(String.format("The attribute value '%s' is not valid", attributeValue));
    }
}
