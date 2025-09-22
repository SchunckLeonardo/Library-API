package io.github.libraryapi.service

import io.github.libraryapi.controller.dto.*
import io.github.libraryapi.exceptions.BookNotFoundException
import io.github.libraryapi.model.Book
import io.github.libraryapi.model.enums.BookGenreEnum
import io.github.libraryapi.model.toBookDTO
import io.github.libraryapi.model.toGetBookResponseDTO
import io.github.libraryapi.repository.BookRepository
import io.github.libraryapi.security.SecurityService
import io.github.libraryapi.service.specs.BookSpecs
import io.github.libraryapi.validator.BookValidator
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val authorService: AuthorService,
    private val bookValidator: BookValidator,
    private val bookSpecs: BookSpecs,
    private val securityService: SecurityService
) {

    private val log = LoggerFactory.getLogger(BookService::class.java)

    fun save(request: RegisterBookDTO): BookDTO {
        bookValidator.validateIsbn(request.isbn ?: "")

        log.info("save - $request")

        return bookRepository.save(
            Book(
                isbn = request.isbn,
                title = request.title,
                publishedDate = request.publishedDate,
                genre = BookGenreEnum.valueOf(request.genre ?: ""),
                price = request.price ?: throw IllegalArgumentException("Price is required"),
                author = authorService.getById(request.authorId ?: throw IllegalArgumentException("Author ID is required")),
                user = securityService.getUserSigned() ?: throw IllegalStateException("No user logged in")
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
        book.genre = dto.genre?.let { BookGenreEnum.getValue(it) } ?: book.genre
        book.price = dto.price ?: book.price
        book.author = dto.authorId?.let { authorService.getById(it) } ?: book.author

        bookRepository.save(book)
    }

    private fun getBookById(bookId: UUID): Book =
        bookRepository
            .findById(bookId)
            .orElseThrow { throw BookNotFoundException() }

}