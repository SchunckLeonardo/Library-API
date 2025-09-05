package io.github.libraryapi.controller

import io.github.libraryapi.controller.dto.AuthorDTO
import io.github.libraryapi.controller.dto.ResponseError
import io.github.libraryapi.exceptions.DuplicatedRegistryException
import io.github.libraryapi.exceptions.OperationNotAllowedException
import io.github.libraryapi.model.toAuthorDTO
import io.github.libraryapi.service.AuthorService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/authors")
class AuthorController(
    private val authorService: AuthorService
) {

    @PostMapping
    fun save(
        @RequestBody @Valid author: AuthorDTO
    ): ResponseEntity<ResponseError> {
        try {
            val authorSaved = authorService.save(
                author.toAuthor()
            )

            return ResponseEntity.created(
                URI.create("/authors/${authorSaved.id}")
            ).build()
        } catch (e: DuplicatedRegistryException) {
            val errorDTO = ResponseError.conflict("${author.name} already registered")
            return ResponseEntity.status(errorDTO.status).body(errorDTO)
        }
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable("id") id: String
    ): ResponseEntity<AuthorDTO> {
        val authorId = UUID.fromString(id)

        val author = authorService.getById(authorId)

        if (!author.isPresent) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(
            author.get().toAuthorDTO()
        )
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: String
    ): ResponseEntity<ResponseError> {
        val authorId = UUID.fromString(id)

        val authorOptional = authorService.getById(authorId)

        if (authorOptional.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        try {
            authorService.delete(authorOptional.get())
        } catch (e: OperationNotAllowedException) {
            val errorDTO = ResponseError.defaultResponse(e.message)
            return ResponseEntity.status(errorDTO.status).body(errorDTO)
        }
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun findAuthor(
        @RequestParam("name", required = false) name: String?,
        @RequestParam("nationality", required = false) nationality: String?,
    ): ResponseEntity<List<AuthorDTO>> {
        val authors = authorService.find(name ?: "", nationality ?: "")

        return ResponseEntity.ok(
            authors.map { it.toAuthorDTO() }
        )
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: String,
        @RequestBody @Valid author: AuthorDTO
    ): ResponseEntity<ResponseError> {
        val authorId = UUID.fromString(id)
        val authorOptional = authorService.getById(authorId)

        if (!authorOptional.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val existingAuthor = authorOptional.get()
        existingAuthor.name = author.name
        existingAuthor.birthDate = author.birthDate
        existingAuthor.nationality = author.nationality

        try {
            authorService.update(existingAuthor).toAuthorDTO()
        } catch (e: DuplicatedRegistryException) {
            val errorDTO = ResponseError.conflict("${author.name} already registered")
            return ResponseEntity.status(errorDTO.status).body(errorDTO)
        }

        return ResponseEntity.noContent().build()
    }

}