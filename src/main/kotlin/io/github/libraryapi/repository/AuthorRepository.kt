package io.github.libraryapi.repository

import io.github.libraryapi.model.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.util.Optional
import java.util.UUID

interface AuthorRepository : JpaRepository<Author, UUID> {

    fun findByNameAndBirthDateAndNationality(name: String, birthDate: LocalDate, nationality: String): Optional<Author>

    @Query("""
        SELECT a
        FROM Author a
        WHERE
            (:name IS NULL OR a.name LIKE CONCAT('%', :name, '%'))
        AND
            (:nationality IS NULL OR :nationality = '' OR a.nationality = :nationality)
    """)
    fun findAnExistingAuthors(name: String, nationality: String) : List<Author>

}