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

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandbardErrorResponse> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        return responseErrorEntity(HttpStatus.BAD_REQUEST, e, request, "Object not found");
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<StandbardErrorResponse> notFoundStatusFeignClient(HttpServletRequest request, FeignException.NotFound e) {
        return responseErrorEntity(HttpStatus.NOT_FOUND, e, request, "CPF isn't valid");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandbardErrorResponse> constraintException(HttpServletRequest request, ConstraintViolationException e) {
        return responseErrorEntity(HttpStatus.INTERNAL_SERVER_ERROR, e, request, "Some attribute is invalid");
    }

    @ExceptionHandler(CpfMemberRegistredException.class)
    public ResponseEntity<StandbardErrorResponse> memberIsRegistred(HttpServletRequest request,CpfMemberRegistredException e){
        return responseErrorEntity(HttpStatus.CONFLICT, e, request, "CPF already is registered, use other value");
    }

    @ExceptionHandler(OpenSessionException.class)
    public ResponseEntity<StandbardErrorResponse> notPossibleOpenVotation(HttpServletRequest request, OpenSessionException e){
        return responseErrorEntity(HttpStatus.BAD_REQUEST, e, request, "Cannot open poll without members");
    }

    private ResponseEntity<StandbardErrorResponse> responseErrorEntity(
            HttpStatus httpStatus, Exception exception,
            HttpServletRequest request, String messageError) {

        var error = new StandbardErrorResponse(Instant.now(), httpStatus.value(), messageError, exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(error);
    }
}
