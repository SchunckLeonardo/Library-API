package io.github.libraryapi.controller

import io.github.libraryapi.controller.dto.BookDTO
import io.github.libraryapi.controller.dto.GetBookResponseDTO
import io.github.libraryapi.controller.dto.RegisterBookDTO
import io.github.libraryapi.service.BookService
import jakarta.validation.Valid
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

    //@GetMapping
    //fun findBooks(): ResponseEntity<List<GetBookResponseDTO>> {

    //}


}