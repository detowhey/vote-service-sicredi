package br.com.sicredi.api.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandbardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        return responseErrorEntity(HttpStatus.NOT_FOUND, e.getMessage(), request, "Resource not found");
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandbardError> dataBaseError(DataBaseException e, HttpServletRequest request) {
        return responseErrorEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), request, "Database error");
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandbardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        return responseErrorEntity(HttpStatus.BAD_REQUEST, e.getMessage(), request, "Member already registered with this CPF");
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<StandbardError> notFoundStatusFeignClient(HttpServletRequest request) {
        String cpf = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE).toString().replaceAll("[^0-9]+", "");
        return responseErrorEntity(
                HttpStatus.NOT_FOUND,
                "The following CPF number " + cpf + " isn't valid",
                request,
                "CPF isn't valid"
        );
    }

    private ResponseEntity<StandbardError> responseErrorEntity(
            HttpStatus httpStatus, String exceptionMessage,
            HttpServletRequest request, String messageError) {

        StandbardError error = new StandbardError(Instant.now(), httpStatus.value(), messageError, exceptionMessage, request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(error);
    }
}
