package br.com.sicredi.api.exception.handler;

import br.com.sicredi.api.exception.*;
import br.com.sicredi.api.exception.error.StandardErrorResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.Instant;

@ControllerAdvice
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

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<StandardErrorResponse> sendNotFoundStatusFeignClient(HttpServletRequest request, FeignException.NotFound e) {
        return buildResponseErrorEntity(HttpStatus.NOT_FOUND, e, request, "CPF isn't valid");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardErrorResponse> sendConstraintException(HttpServletRequest request, ConstraintViolationException e) {
        return buildResponseErrorEntity(HttpStatus.INTERNAL_SERVER_ERROR, e, request, "Some attribute is invalid");
    }

    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<StandardErrorResponse> memberIsRegistered(HttpServletRequest request, InvalidCpfException e) {
        return buildResponseErrorEntity(HttpStatus.BAD_REQUEST, e, request, "Invalid CPF");
    }

    @ExceptionHandler(DuplicateVoteException.class)
    public ResponseEntity<StandardErrorResponse> duplicatedVote(HttpServletRequest request, DuplicateVoteException e) {
        return buildResponseErrorEntity(HttpStatus.CONFLICT, e, request, "The vote is duplicated");
    }

    @ExceptionHandler(InvalidRulingAttributeException.class)
    public ResponseEntity<StandardErrorResponse> invalidateRulingAttribute(HttpServletRequest request, InvalidRulingAttributeException e) {
        return buildResponseErrorEntity(HttpStatus.BAD_REQUEST, e, request, "Attribute value is not valid");
    }

    private ResponseEntity<StandardErrorResponse> buildResponseErrorEntity(
            HttpStatus httpStatus, Exception exception,
            HttpServletRequest request, String messageError) {

        var error = new StandardErrorResponse(Instant.now(), httpStatus.value(), messageError, exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(error);
    }
}
