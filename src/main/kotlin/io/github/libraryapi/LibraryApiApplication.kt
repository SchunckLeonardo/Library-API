package io.github.libraryapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class LibraryApiApplication

fun main(args: Array<String>) {
	runApplication<LibraryApiApplication>(*args)
}
