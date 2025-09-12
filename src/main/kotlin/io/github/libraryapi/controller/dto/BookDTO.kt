package io.github.libraryapi.controller.dto

import io.github.libraryapi.model.enums.BookGenreEnum
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class BookDTO(
    val id: UUID?,
    val isbn: String?,
    val title: String?,
    val publishedDate: LocalDate?,
    val genre: BookGenreEnum?,
    val price: BigDecimal?,
    val author: AuthorDTO?
)
