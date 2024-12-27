package br.com.sicredi.generate_cpf.service

import br.com.sicredi.generate_cpf.exception.InvalidCpfException
import br.com.sicredi.generate_cpf.model.Status
import br.com.sicredi.generate_cpf.model.enu.StatusEnum
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class CpfService {

    val log: Logger = LoggerFactory.getLogger(CpfService::class.java)

    fun generateCpfStatus(cpf: String): Status {
        if (!this.validateCpf(cpf)) {
            throw InvalidCpfException("Cpf is not valid").also { log.error("Invalid CPF") }
        }

        val textStatus = if (Random.nextBoolean()) StatusEnum.ABLE_TO_VOTE.name else StatusEnum.UNABLE_TO_VOTE.name
        return Status(textStatus).also { log.info("CPF is valid") }
    }

    private fun validateCpf(document: String): Boolean {
        if (document.isEmpty()) return false

        val numbers = document.filter { it.isDigit() }.map {
            it.toString().toInt()
        }

        if (numbers.size != 11) return false

        if (numbers.all { it == numbers[0] }) return false

        val dv1 = ((0..8).sumOf { (it + 1) * numbers[it] }).rem(11).let {
            if (it >= 10) 0 else it
        }

        val dv2 = ((0..8).sumOf { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
            if (it >= 10) 0 else it
        }

        return numbers[9] == dv1 && numbers[10] == dv2
    }
}
