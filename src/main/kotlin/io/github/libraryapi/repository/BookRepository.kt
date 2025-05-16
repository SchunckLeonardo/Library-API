package io.github.libraryapi.repository

import io.github.libraryapi.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface BookRepository : JpaRepository<Book, UUID> {
}