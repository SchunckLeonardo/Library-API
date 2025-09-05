package io.github.libraryapi.model.factory

import io.github.libraryapi.model.Author
import java.time.LocalDate
import java.util.*

data class AuthorFactory(
    val name: String,
    val nationality: String,
    val birthDate: LocalDate
) {

    companion object {

        fun create(
            name: String = "John Doe",
            nationality: String = "American",
            birthDate: LocalDate = LocalDate.of(2005, 2, 28)
        ): Author {
            val author = Author()

            author.id = UUID.randomUUID()
            author.name = name
            author.nationality = nationality
            author.birthDate = birthDate

            return author
        }

        fun createWithNameAndNationality(
            name: String,
            nationality: String
        ): Author {
            val author = Author()

            author.name = name
            author.nationality = nationality

            return author
        }

    }

}