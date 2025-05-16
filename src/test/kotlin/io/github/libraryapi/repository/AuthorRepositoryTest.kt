package io.github.libraryapi.repository

import io.github.libraryapi.model.Author
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import java.time.LocalDate
import java.util.*
import kotlin.test.Test

@SpringBootTest
private class AuthorRepositoryTest {

    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @Test
    fun saveTest() {
        val author = Author()
        author.name = "John Doe"
        author.nationality = "American"
        author.birthDate = LocalDate.of(1980, 1, 1)

        val authorSaved = authorRepository.save(author)
        println("Author saved: $authorSaved")
    }

    @Test
    fun updateTest() {
        val id = UUID.fromString("93fdfb74-12a9-4167-8db0-be927c7bb54a")

        val optionalAuthor: Optional<Author> = authorRepository.findById(id)

        assert(optionalAuthor.isPresent)

        val author = optionalAuthor.get()
        author.name = "Jane Doe"

        val authorSaved = authorRepository.save(author)

        println("Author updated: $authorSaved")
    }

    @Test
    fun listTest() {
        val authorsList: List<Author> = authorRepository.findAll()
        authorsList.forEach { author -> println(author.toString()) }
    }

    @Test
    fun deleteByIdTest() {
        val id = UUID.fromString("93fdfb74-12a9-4167-8db0-be927c7bb54a")

        authorRepository.deleteById(id)
    }

}