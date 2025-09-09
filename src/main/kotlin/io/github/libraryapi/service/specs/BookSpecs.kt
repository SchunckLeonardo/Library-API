package io.github.libraryapi.service.specs

import io.github.libraryapi.controller.dto.FindBooksRequestDTO
import io.github.libraryapi.model.Author
import io.github.libraryapi.model.Book
import io.github.libraryapi.model.BookGenre
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class BookSpecs {

    private fun isbnEqual(isbn: String): Specification<Book> =
        Specification<Book> { root, _, cb ->
            cb.equal(root.get<String>("isbn"), isbn)
        }

    private fun titleLike(title: String): Specification<Book> =
        Specification<Book> { root, _, cb ->
            cb.like(
                cb.upper(
                    root.get<String>("title")
                ),
                "%${title.uppercase()}%"
            )
        }

    private fun genreEqual(genre: BookGenre): Specification<Book> =
        Specification<Book> { root, _, cb ->
            cb.equal(root.get<BookGenre>("genre"), genre)
        }

    private fun yearPublishedDateEqual(yearPublishedDate: Int): Specification<Book> =
        Specification<Book> { root, _, cb ->
            cb.equal(
                cb.function("to_char", String::class.java, root.get<LocalDate>("publishedDate"), cb.literal("YYYY")),
                yearPublishedDate.toString()
            )
        }

    private fun authorNameLike(authorName: String): Specification<Book> =
        Specification<Book> { root, _, cb ->
            cb.like(
                cb.upper(root.get<Author>("author").get<String>("name")),
                "%${authorName.uppercase()}%"
            )
        }

    fun getSpecsAndValidate(request: FindBooksRequestDTO): Specification<Book> {
        val specs = listOfNotNull(
            request.isbn?.let { isbnEqual(it) },
            request.title?.let { titleLike(it) },
            request.genre?.let { genreEqual(BookGenre.getValue(it)) },
            request.yearPublishedDate?.let { yearPublishedDateEqual(it) },
            request.authorName?.let { authorNameLike(it) }
        )

        return specs.fold(Specification.where<Book> { _, _, cb -> cb.conjunction() }) { acc, spec ->
            acc.and(spec)
        }
    }

}