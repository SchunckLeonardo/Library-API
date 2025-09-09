package io.github.libraryapi.service

import io.github.libraryapi.controller.dto.*
import io.github.libraryapi.exceptions.BookNotFoundException
import io.github.libraryapi.model.Book
import io.github.libraryapi.model.BookGenre
import io.github.libraryapi.model.toBookDTO
import io.github.libraryapi.model.toGetBookResponseDTO
import io.github.libraryapi.repository.BookRepository
import io.github.libraryapi.service.specs.BookSpecs
import io.github.libraryapi.validator.BookValidator
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val authorService: AuthorService,
    private val bookValidator: BookValidator,
    private val bookSpecs: BookSpecs
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
        getBookById(bookId)
            .toGetBookResponseDTO()

    fun delete(bookId: UUID) {
        val book = bookRepository
            .findById(bookId)
            .orElseThrow { throw BookNotFoundException() }

        bookRepository.delete(book)
    }

    fun findBooks(request: FindBooksRequestDTO): List<GetBookResponseDTO> =
        bookRepository.findAll(
            bookSpecs.getSpecsAndValidate(request),
            PageRequest.of(
                request.page,
                request.size,
                Sort.Direction.ASC,
                "title"
            )
        ).map { it.toGetBookResponseDTO() }.toList()

    fun updateBook(bookId: UUID, dto: BookUpdateDTO) {
        val book = getBookById(bookId)

        dto.isbn?.let { bookValidator.validateIsbn(it) }

        book.isbn = dto.isbn ?: book.isbn
        book.title = dto.title ?: book.title
        book.publishedDate = dto.publishedDate ?: book.publishedDate
        book.genre = dto.genre?.let { BookGenre.getValue(it) } ?: book.genre
        book.price = dto.price ?: book.price
        book.author = dto.authorId?.let { authorService.getById(it) } ?: book.author

        bookRepository.save(book)
    }

    private fun getBookById(bookId: UUID): Book =
        bookRepository
            .findById(bookId)
            .orElseThrow { throw BookNotFoundException() }

}