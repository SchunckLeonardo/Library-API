package io.github.libraryapi.service.specs

import io.github.libraryapi.model.Author
import io.github.libraryapi.model.Book
import io.github.libraryapi.model.BookGenre
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate

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

        fun yearPublishedDateEqual(yearPublishedDate: Int): Specification<Book> =
            Specification<Book> { root, _, cb ->
                cb.equal(
                    cb.function("to_char", String::class.java, root.get<LocalDate>("publishedDate"), cb.literal("YYYY")),
                    yearPublishedDate.toString()
                )
            }

        fun authorNameLike(authorName: String): Specification<Book> =
            Specification<Book> { root, _, cb ->
                cb.like(
                    cb.upper(root.get<Author>("author").get<String>("name")),
                    "%${authorName.uppercase()}%"
                )
            }

    }

}