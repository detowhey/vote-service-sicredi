package br.com.sicredi.api.exception.handler;

import br.com.sicredi.api.exception.*;
import br.com.sicredi.api.exception.error.StandardErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardErrorResponse> sendResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        return buildResponseErrorEntity(HttpStatus.NOT_FOUND, e, request, "Resource not found");
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardErrorResponse> sendDataBaseError(DataBaseException e, HttpServletRequest request) {
        return buildResponseErrorEntity(HttpStatus.INTERNAL_SERVER_ERROR, e, request, "Database error");
    }

    @ExceptionHandler(RulingNotFoundException.class)
    public ResponseEntity<StandardErrorResponse> sendRulingNotFound(RulingNotFoundException e, HttpServletRequest request) {
        return buildResponseErrorEntity(HttpStatus.BAD_REQUEST, e, request, "Ruling not found");
    }

    @ExceptionHandler(SessionAlreadyStartedException.class)
    public ResponseEntity<StandardErrorResponse> sessionIsStarted(SessionAlreadyStartedException e, HttpServletRequest request) {
        return buildResponseErrorEntity(HttpStatus.CONFLICT, e, request, "Session has already started");
    }

    @ExceptionHandler(SessionClosedException.class)
    public ResponseEntity<StandardErrorResponse> sessionClosed(SessionClosedException e, HttpServletRequest request) {
        return buildResponseErrorEntity(HttpStatus.CONFLICT, e, request, "Session is closed");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardErrorResponse> sendConstraintException(HttpServletRequest request, ConstraintViolationException e) {
        return buildResponseErrorEntity(HttpStatus.INTERNAL_SERVER_ERROR, e, request, "Some attribute is invalid");
    }

    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<StandardErrorResponse> sendInvalidCpfException(HttpServletRequest request, InvalidCpfException e) {
        return buildResponseErrorEntity(HttpStatus.BAD_REQUEST, e, request, "Invalid CPF");
    }

    @ExceptionHandler(DuplicateVoteException.class)
    public ResponseEntity<StandardErrorResponse> sendDuplicatedVote(HttpServletRequest request, DuplicateVoteException e) {
        return buildResponseErrorEntity(HttpStatus.CONFLICT, e, request, "The vote is duplicated");
    }

    @ExceptionHandler(InvalidRulingAttributeException.class)
    public ResponseEntity<StandardErrorResponse> invalidateRulingAttribute(HttpServletRequest request, InvalidRulingAttributeException e) {
        return buildResponseErrorEntity(HttpStatus.BAD_REQUEST, e, request, "Attribute value is not valid");
    }

    private ResponseEntity<StandardErrorResponse> buildResponseErrorEntity(
            HttpStatus httpStatus, Exception exception,
            HttpServletRequest request, String messageError) {

        log.error(exception.getMessage(), exception);
        var responseError = new StandardErrorResponse(Instant.now(), httpStatus.value(), messageError, exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(responseError);
    }
}
