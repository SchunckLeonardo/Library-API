package io.github.libraryapi.controller.dto

data class FindBooksRequestDTO(
    val isbn: String? = null,
    val title: String? = null,
    val authorName: String? = null,
    val genre: String? = null,
    val yearPublishedDate: Int? = null,
    val page: Int,
    val size: Int
)
