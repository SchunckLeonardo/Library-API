package io.github.libraryapi.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import org.hibernate.validator.constraints.ISBN
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class BookUpdateDTO(
    @field:ISBN
    val isbn: String?,

    val title: String?,

    @field:Past(message = "cannot be a future date")
    val publishedDate: LocalDate?,
    val genre: String?,
    val price: BigDecimal?,

    val authorId: UUID?
)
