package io.github.libraryapi.controller.dto

import io.github.libraryapi.model.BookGenre
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import org.hibernate.validator.constraints.ISBN
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class RegisterBookDTO(
    @ISBN
    @NotBlank(message = "required field")
    val isbn: String,

    @NotBlank(message = "required field")
    val title: String,

    @NotNull(message = "required field")
    @Past(message = "cannot be a future date")
    val publishedDate: LocalDate,
    val genre: BookGenre,
    val price: BigDecimal,

    @NotNull(message = "required field")
    val authorId: UUID
)
