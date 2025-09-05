package io.github.libraryapi.exceptions

data class DuplicatedRegistryException(override val message: String = "Entity already exists with some credentials") : RuntimeException(message)