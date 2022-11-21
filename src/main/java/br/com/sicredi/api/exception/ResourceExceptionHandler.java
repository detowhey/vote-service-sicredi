package br.com.sicredi.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandarError> resourceNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        return responseErrorEntity(HttpStatus.NOT_FOUND, exception, request, "Resource not found");
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandarError> dataBaseError(DataBaseException exception, HttpServletRequest request) {
        return responseErrorEntity(HttpStatus.BAD_REQUEST, exception, request, "Database error");
    }


    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandarError> objectNotFound(ObjectNotFoundException exception, HttpServletRequest request) {
        return responseErrorEntity(HttpStatus.BAD_REQUEST, exception, request, "Member already registered with this CPF");
    }

    private ResponseEntity<StandarError> responseErrorEntity(
            HttpStatus httpStatus, RuntimeException exception,
            HttpServletRequest request, String messageError) {

        StandarError error = new StandarError(Instant.now(), httpStatus.value(), messageError, exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(error);
    }
}
