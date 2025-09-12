package io.github.libraryapi.model.enums

enum class BookGenreEnum {
    FICTION,
    FANTASY,
    MYSTERY,
    ROMANCE,
    BIOGRAPHY,
    SCIENCE;

    companion object {

        fun getValue(value: String): BookGenreEnum {
            return BookGenreEnum.valueOf(value.uppercase())
        }

    }

}