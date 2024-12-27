package br.com.sicredi.api.exception.handler;

import br.com.sicredi.api.exception.error.ErrorParameter;
import br.com.sicredi.api.exception.error.ErrorParameterResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ArgumentsInvalidHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ErrorParameter> errorObjects = getErrors(ex);
        ErrorParameterResponse errorResponse = getErrorResponse(status, errorObjects);
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, status);
    }

    private List<ErrorParameter> getErrors(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorParameter(
                        error.getDefaultMessage(),
                        error.getField(),
                        error.getRejectedValue()
                )).toList();
    }

    private ErrorParameterResponse getErrorResponse(HttpStatusCode status, List<ErrorParameter> errors) {
        return new ErrorParameterResponse(
                Instant.now(),
                status.value(),
                "Request has invalid fields",
                errors
        );
    }
}
