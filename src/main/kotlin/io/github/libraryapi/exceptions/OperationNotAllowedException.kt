package io.github.libraryapi.exceptions

data class OperationNotAllowedException(override val message: String) : RuntimeException(message)