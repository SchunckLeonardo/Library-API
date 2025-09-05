package io.github.libraryapi.controller.dto

import io.github.libraryapi.model.BookGenre
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class GetBookResponseDTO(
    val id: UUID? = null,
    val isbn: String? = null,
    val title: String? = null,
    val publishedDate: LocalDate? = null,
    val genre: BookGenre? = null,
    val price: BigDecimal? = null,
    val author: GetBookAuthorDTO? = null
)
