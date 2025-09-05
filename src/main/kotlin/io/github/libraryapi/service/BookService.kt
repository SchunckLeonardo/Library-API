package io.github.libraryapi.service

import io.github.libraryapi.controller.dto.BookDTO
import io.github.libraryapi.controller.dto.FindBooksRequestDTO
import io.github.libraryapi.controller.dto.GetBookResponseDTO
import io.github.libraryapi.controller.dto.RegisterBookDTO
import io.github.libraryapi.exceptions.BookNotFoundException
import io.github.libraryapi.model.Book
import io.github.libraryapi.model.BookGenre
import io.github.libraryapi.model.toBookDTO
import io.github.libraryapi.model.toGetBookResponseDTO
import io.github.libraryapi.repository.BookRepository
import io.github.libraryapi.service.specs.BookSpecs
import io.github.libraryapi.validator.BookValidator
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val authorService: AuthorService,
    private val bookValidator: BookValidator
) {

    fun save(request: RegisterBookDTO): BookDTO {
        bookValidator.validateIsbn(request.isbn ?: "")

        return bookRepository.save(
            Book(
                isbn = request.isbn,
                title = request.title,
                publishedDate = request.publishedDate,
                genre = BookGenre.valueOf(request.genre ?: ""),
                price = request.price ?: throw IllegalArgumentException("Price is required"),
                author = authorService.getById(request.authorId ?: throw IllegalArgumentException("Author ID is required"))
            )
        ).toBookDTO()
    }

    fun getById(bookId: UUID): GetBookResponseDTO =
        bookRepository
            .findById(bookId)
            .orElseThrow { throw BookNotFoundException() }
            .toGetBookResponseDTO()

    fun delete(bookId: UUID) {
        val book = bookRepository
            .findById(bookId)
            .orElseThrow { throw BookNotFoundException() }

        bookRepository.delete(book)
    }

    fun findBooks(request: FindBooksRequestDTO): List<GetBookResponseDTO> {
        val specs = Specification
            .where<Book> { _, _, cb -> cb.conjunction() }

        if (request.isbn != null) {
            specs.and(BookSpecs.isbnEqual(request.isbn))
        }

        if (request.title != null) {
            specs.and(BookSpecs.titleLike(request.title))
        }

        if (request.genre != null) {
            specs.and(BookSpecs.genreEqual(BookGenre.valueOf(request.genre.name)))
        }

        return bookRepository.findAll(specs).map { it.toGetBookResponseDTO() }
    }

}