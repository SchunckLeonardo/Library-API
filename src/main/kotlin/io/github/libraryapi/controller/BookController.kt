package io.github.libraryapi.controller

import io.github.libraryapi.controller.dto.RegisterBookDTO
import io.github.libraryapi.controller.dto.ResponseError
import io.github.libraryapi.exceptions.DuplicatedRegistryException
import io.github.libraryapi.service.BookService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(
    private val bookService: BookService
) {

    @PostMapping
    fun save(
        @RequestBody @Valid request: RegisterBookDTO
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().build()
        } catch (ex: DuplicatedRegistryException) {
            val errorDTO = ResponseError.conflict(ex.message)
            ResponseEntity.status(errorDTO.status).body(errorDTO)
        }
    }


}