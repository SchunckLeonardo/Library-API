package io.github.libraryapi.repository

import io.github.libraryapi.model.Author
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AuthorRepository : JpaRepository<Author, UUID> {
}