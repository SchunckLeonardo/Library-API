package io.github.libraryapi.controller.dto

import io.github.libraryapi.model.Author
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.util.*

data class AuthorDTO (
    val id: UUID?,

    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(max = 100, message = "Name cannot exceed 100 characters")
    val name: String,

    @field:NotNull(message = "Birth date cannot be null")
    @field:Past(message = "Birth date must be in the past")
    val birthDate: LocalDate,

    @field:NotBlank(message = "Nationality cannot be blank")
    @field:Size(max = 50, message = "Nationality cannot exceed 50 characters")
    val nationality: String
) {
    fun toAuthor(): Author {
        val author = Author()
        author.name = this.name
        author.birthDate = this.birthDate
        author.nationality = this.nationality

        return author
    }
}