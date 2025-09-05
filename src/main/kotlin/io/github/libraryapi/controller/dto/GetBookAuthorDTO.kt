package io.github.libraryapi.controller.dto

import java.time.LocalDate

data class GetBookAuthorDTO(
    val name: String? = null,
    val birthDate: LocalDate? = null,
    val nationality: String? = null
)
