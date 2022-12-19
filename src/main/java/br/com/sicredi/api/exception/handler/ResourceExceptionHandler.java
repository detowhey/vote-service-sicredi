package br.com.sicredi.api.exception.handler;

import br.com.sicredi.api.exception.*;
import br.com.sicredi.api.exception.error.StandbardErrorResponse;
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
    public ResponseEntity<StandbardErrorResponse> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        return responseErrorEntity(HttpStatus.NOT_FOUND, e, request, "Resource not found");
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandbardErrorResponse> dataBaseError(DataBaseException e, HttpServletRequest request) {
        return responseErrorEntity(HttpStatus.INTERNAL_SERVER_ERROR, e, request, "Database error");
    }

    @ExceptionHandler(RulingNotFoundException.class)
    public ResponseEntity<StandbardErrorResponse> rulingNotFound(RulingNotFoundException e, HttpServletRequest request) {
        return responseErrorEntity(HttpStatus.BAD_REQUEST, e, request, "Ruling not found");
    }

    @ExceptionHandler(SessionAlreadyStartedException.class)
    public ResponseEntity<StandbardErrorResponse> sessionIsStarted(SessionAlreadyStartedException e, HttpServletRequest request) {
        return responseErrorEntity(HttpStatus.CONFLICT, e, request, "Session has already started");
    }

    @ExceptionHandler(SessionClosedException.class)
    public ResponseEntity<StandbardErrorResponse> sessionClosed(SessionClosedException e, HttpServletRequest request) {
        return responseErrorEntity(HttpStatus.CONFLICT, e, request, "Session is closed");
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<StandbardErrorResponse> notFoundStatusFeignClient(HttpServletRequest request, FeignException.NotFound e) {
        return responseErrorEntity(HttpStatus.NOT_FOUND, e, request, "CPF isn't valid");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandbardErrorResponse> constraintException(HttpServletRequest request, ConstraintViolationException e) {
        return responseErrorEntity(HttpStatus.INTERNAL_SERVER_ERROR, e, request, "Some attribute is invalid");
    }

    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<StandbardErrorResponse> memberIsRegistred(HttpServletRequest request, InvalidCpfException e) {
        return responseErrorEntity(HttpStatus.BAD_REQUEST, e, request, "Invalid CPF");
    }

    @ExceptionHandler(DuplicateVoteException.class)
    public ResponseEntity<StandbardErrorResponse> duplicatedVote(HttpServletRequest request, DuplicateVoteException e) {
        return responseErrorEntity(HttpStatus.CONFLICT, e, request, "The vote is duplicated");
    }

    private ResponseEntity<StandbardErrorResponse> responseErrorEntity(
            HttpStatus httpStatus, Exception exception,
            HttpServletRequest request, String messageError) {

        var error = new StandbardErrorResponse(Instant.now(), httpStatus.value(), messageError, exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(error);
    }
}
