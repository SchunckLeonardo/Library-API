package io.github.libraryapi.validator

import io.github.libraryapi.exceptions.DuplicatedRegistryException
import io.github.libraryapi.repository.BookRepository
import org.springframework.stereotype.Component

@Component
class BookValidator(
    private val bookRepository: BookRepository
) {

    fun validateIsbn(isbn: String) {
        if (existsBookRegistered(isbn)) {
            throw DuplicatedRegistryException("Book with ISBN $isbn already registered")
        }
    }

    private fun existsBookRegistered(isbn: String): Boolean {
        return bookRepository.findByIsbn(isbn).isPresent
    }

}