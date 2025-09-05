package io.github.libraryapi.exceptions

data class AuthorNotFoundException(
    override val message: String = "Author not found"
): RuntimeException(message)