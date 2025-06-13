package io.github.libraryapi.exceptions

data class DuplicatedRegistryException(override val message: String) : RuntimeException(message)