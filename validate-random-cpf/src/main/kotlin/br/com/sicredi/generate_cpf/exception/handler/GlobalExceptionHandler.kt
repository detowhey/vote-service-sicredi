package br.com.sicredi.generate_cpf.exception.handler

import br.com.sicredi.generate_cpf.exception.ErrorResponse
import br.com.sicredi.generate_cpf.exception.InvalidCpfException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.NoHandlerFoundException
import java.time.Instant


@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvalidCpfException::class)
    fun sendInvalidCpf(exception: InvalidCpfException, httpServletRequest: HttpServletRequest): ErrorResponse {
        return this.buildResponseErrorEntity(
            HttpStatus.NOT_FOUND,
            exception,
            httpServletRequest,
            "Invalid CPF"
        )
    }

    private fun buildResponseErrorEntity(
        httpStatus: HttpStatus,
        exception: Exception?,
        request: HttpServletRequest,
        messageError: String
    ): ErrorResponse {
        return ErrorResponse(
            Instant.now(),
            httpStatus.value(),
            messageError,
            exception?.message.orEmpty(),
            request.requestURI
        ).also {
            logger.error(messageError)
        }
    }
}