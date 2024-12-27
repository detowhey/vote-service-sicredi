package br.com.sicredi.generate_cpf.exception

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant

data class ErrorResponse(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy - HH:mm:ss", timezone = "America/Sao_Paulo")
    val timestamp: Instant,
    val statusCode: Int,
    val error: String,
    val message: String,
    val path: String
)
