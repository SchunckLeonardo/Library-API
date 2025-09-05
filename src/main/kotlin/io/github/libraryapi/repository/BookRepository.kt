package io.github.libraryapi.repository

import io.github.libraryapi.model.Author
import io.github.libraryapi.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.Optional
import java.util.UUID

interface BookRepository : JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {

    fun existsByAuthor(author: Author): Boolean
    fun findByIsbn(isbn: String): Optional<Book>

}