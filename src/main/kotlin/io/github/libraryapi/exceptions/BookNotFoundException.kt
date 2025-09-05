package io.github.libraryapi.exceptions

data class BookNotFoundException(
    override val message: String = "Book not found"
): RuntimeException(message)
