package io.github.libraryapi.model

enum class BookGenre {
    FICTION,
    FANTASY,
    MYSTERY,
    ROMANCE,
    BIOGRAPHY,
    SCIENCE;

    companion object {

        fun getValue(value: String): BookGenre {
            return BookGenre.valueOf(value.uppercase())
        }

    }

}