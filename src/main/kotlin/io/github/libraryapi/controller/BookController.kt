package io.github.libraryapi.controller

import io.github.libraryapi.controller.dto.*
import io.github.libraryapi.model.BookGenre
import io.github.libraryapi.service.BookService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/books")
class BookController(
    private val bookService: BookService
) {

    @PostMapping
    fun save(
        @RequestBody @Valid request: RegisterBookDTO
    ): ResponseEntity<BookDTO> {
        val book = bookService.save(request)
        val uri = URI.create("/books/${book.id}")

        return ResponseEntity.created(uri).body(book)
    }

    @GetMapping("/{id}")
    fun getBookById(
        @PathVariable("id") id: String
    ): ResponseEntity<GetBookResponseDTO> {
        val uuid = UUID.fromString(id)
        return ResponseEntity.ok(bookService.getById(uuid))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String): ResponseEntity<Any> {
        val uuid = UUID.fromString(id)
        bookService.delete(uuid)
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun findBooks(
        @RequestParam(required = false) isbn: String?,
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) genre: String?,
        @RequestParam(required = false) authorName: String?,
        @RequestParam(required = false) yearPublishedDate: Int?,
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): ResponseEntity<List<GetBookResponseDTO>> {
        return ResponseEntity.ok(bookService.findBooks(
            FindBooksRequestDTO(
                isbn = isbn,
                title = title,
                genre = genre,
                authorName = authorName,
                yearPublishedDate = yearPublishedDate,
                page = page ?: 0,
                size = size ?: 10
            )
        ))
    }

    @PutMapping("/{id}")
    fun updateBook(
        @PathVariable id: String,
        @RequestBody @Valid dto: BookUpdateDTO
    ): ResponseEntity<Any> {
        val uuid = UUID.fromString(id)
        bookService.updateBook(uuid, dto)
        return ResponseEntity.noContent().build()
    }


}