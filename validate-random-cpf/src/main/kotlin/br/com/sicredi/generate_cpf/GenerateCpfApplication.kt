package br.com.sicredi.generate_cpf

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GenerateCpfApplication

fun main(args: Array<String>) {
	runApplication<GenerateCpfApplication>(*args)
}
