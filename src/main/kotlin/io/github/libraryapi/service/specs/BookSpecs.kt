package io.github.libraryapi.service.specs

import io.github.libraryapi.model.Book
import io.github.libraryapi.model.BookGenre
import org.springframework.data.jpa.domain.Specification

class BookSpecs {

    companion object {

        fun isbnEqual(isbn: String): Specification<Book> =
            Specification<Book> { root, _, cb ->
                cb.equal(root.get<String>("isbn"), isbn)
            }

        fun titleLike(title: String): Specification<Book> =
            Specification<Book> { root, _, cb ->
                cb.like(
                    cb.upper(
                        root.get<String>("title")
                    ),
                    "%${title.uppercase()}%"
                )
            }

        fun genreEqual(genre: BookGenre): Specification<Book> =
            Specification<Book> { root, _, cb ->
                cb.equal(root.get<BookGenre>("genre"), genre)
            }



    }

}