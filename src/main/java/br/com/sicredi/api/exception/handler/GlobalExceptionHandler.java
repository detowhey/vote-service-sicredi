package br.com.sicredi.api.exception.handler;

import br.com.sicredi.api.exception.*;
import br.com.sicredi.api.exception.error.ErrorResponse;
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
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> sendResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        return buildResponseErrorEntity(HttpStatus.NOT_FOUND, e, request, "Resource not found");
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ErrorResponse> sendDataBaseError(DataBaseException e, HttpServletRequest request) {
        return buildResponseErrorEntity(HttpStatus.INTERNAL_SERVER_ERROR, e, request, "Database error");
    }

    @ExceptionHandler(RulingNotFoundException.class)
    public ResponseEntity<ErrorResponse> sendRulingNotFound(RulingNotFoundException e, HttpServletRequest request) {
        return buildResponseErrorEntity(HttpStatus.BAD_REQUEST, e, request, "Ruling not found");
    }

    @ExceptionHandler(SessionAlreadyStartedException.class)
    public ResponseEntity<ErrorResponse> sessionIsStarted(SessionAlreadyStartedException e, HttpServletRequest request) {
        return buildResponseErrorEntity(HttpStatus.CONFLICT, e, request, "Session has already started");
    }

    @ExceptionHandler(SessionClosedException.class)
    public ResponseEntity<ErrorResponse> sessionClosed(SessionClosedException e, HttpServletRequest request) {
        return buildResponseErrorEntity(HttpStatus.CONFLICT, e, request, "Session is closed");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> sendConstraintException(HttpServletRequest request, ConstraintViolationException e) {
        return buildResponseErrorEntity(HttpStatus.INTERNAL_SERVER_ERROR, e, request, "Some attribute is invalid");
    }

    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<ErrorResponse> sendInvalidCpfException(HttpServletRequest request, InvalidCpfException e) {
        return buildResponseErrorEntity(HttpStatus.BAD_REQUEST, e, request, "Invalid CPF");
    }

    @ExceptionHandler(DuplicateVoteException.class)
    public ResponseEntity<ErrorResponse> sendDuplicatedVote(HttpServletRequest request, DuplicateVoteException e) {
        return buildResponseErrorEntity(HttpStatus.CONFLICT, e, request, "The vote is duplicated");
    }

    @ExceptionHandler(InvalidRulingAttributeException.class)
    public ResponseEntity<ErrorResponse> invalidateRulingAttribute(HttpServletRequest request, InvalidRulingAttributeException e) {
        return buildResponseErrorEntity(HttpStatus.BAD_REQUEST, e, request, "Attribute value is not valid");
    }

    private ResponseEntity<ErrorResponse> buildResponseErrorEntity(
            HttpStatus httpStatus, Exception exception,
            HttpServletRequest request, String messageError) {

        log.error(exception.getMessage(), exception);
        var responseError = new ErrorResponse(Instant.now(), httpStatus.value(), messageError, exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(responseError);
    }
}
