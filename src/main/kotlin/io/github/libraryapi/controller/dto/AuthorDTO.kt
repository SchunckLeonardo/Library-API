package io.github.libraryapi.controller.dto

import io.github.libraryapi.model.Author
import java.time.LocalDate
import java.util.UUID

data class AuthorDTO (
    val id: UUID?,
    val name: String,
    val birthDate: LocalDate,
    val nationality: String
)

fun AuthorDTO.toAuthor(): Author {
    val author = Author()
    author.name = this.name
    author.birthDate = this.birthDate
    author.nationality = this.nationality

    return author
}