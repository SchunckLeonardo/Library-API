package io.github.libraryapi.service

import io.github.libraryapi.controller.dto.AuthorDTO
import io.github.libraryapi.exceptions.OperationNotAllowedException
import io.github.libraryapi.model.Author
import io.github.libraryapi.model.factory.AuthorFactory
import io.github.libraryapi.repository.AuthorRepository
import io.github.libraryapi.repository.BookRepository
import io.github.libraryapi.validator.AuthorValidator
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID

@Service
class AuthorService(
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository,
    private val authorValidator: AuthorValidator
) {

    fun save(author: Author): Author {
        authorValidator.validate(author)
        return authorRepository.save(author)
    }

    fun getById(authorId: UUID): Optional<Author> {
        return authorRepository.findById(authorId)
    }

    fun delete(author: Author) {
        if (doesAuthorHasBooks(author)) {
            throw OperationNotAllowedException("Cannot delete author with books")
        }
        return authorRepository.delete(author)
    }


    fun find(name: String, nationality: String): List<Author> {
        val matcher = ExampleMatcher
            .matching()
            .withIgnorePaths("id", "birthDate")
            .withIgnoreNullValues()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)

        val authorExample = Example.of(
            AuthorFactory.createWithNameAndNationality(
                name = name,
                nationality = nationality
            ),
            matcher
        )

        return authorRepository.findAll(
            authorExample
        )

//        return authorRepository.findAnExistingAuthors(name, nationality)
    }

    fun update(author: Author): Author {
        authorValidator.validate(author)
        return authorRepository.save(author)
    }

    private fun doesAuthorHasBooks(author: Author): Boolean {
        return bookRepository.existsByAuthor(author)
    }

}
