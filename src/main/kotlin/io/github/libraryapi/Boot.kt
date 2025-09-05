package io.github.libraryapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class Boot

fun main(args: Array<String>) {
	runApplication<Boot>(*args)
}
