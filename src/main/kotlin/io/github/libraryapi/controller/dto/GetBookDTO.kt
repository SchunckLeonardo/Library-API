package io.github.libraryapi.controller.dto

import io.github.libraryapi.model.BookGenre
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class GetBookDTO(
    val id: UUID,
    val isbn: String,
    val title: String,
    val publishedDate: LocalDate,
    val genre: BookGenre,
    val price: BigDecimal,
    val author: AuthorDTO
)
