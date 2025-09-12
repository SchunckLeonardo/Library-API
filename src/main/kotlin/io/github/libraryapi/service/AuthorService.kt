package io.github.libraryapi.service

import io.github.libraryapi.controller.dto.AuthorDTO
import io.github.libraryapi.exceptions.AuthorNotFoundException
import io.github.libraryapi.exceptions.OperationNotAllowedException
import io.github.libraryapi.model.Author
import io.github.libraryapi.model.factory.AuthorFactory
import io.github.libraryapi.repository.AuthorRepository
import io.github.libraryapi.repository.BookRepository
import io.github.libraryapi.security.SecurityService
import io.github.libraryapi.validator.AuthorValidator
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthorService(
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository,
    private val authorValidator: AuthorValidator,
    private val securityService: SecurityService
) {

    fun save(authorDTO: AuthorDTO): Author {
        val author = authorDTO.toAuthor()

        author.user = securityService.getUserSigned()

        authorValidator.validate(author)

        return authorRepository.save(author)
    }

    fun getById(authorId: UUID): Author =
        authorRepository.findById(authorId).orElseThrow { throw AuthorNotFoundException() }

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
