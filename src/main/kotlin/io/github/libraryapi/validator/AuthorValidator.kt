package io.github.libraryapi.validator

import io.github.libraryapi.exceptions.DuplicatedRegistryException
import io.github.libraryapi.model.Author
import io.github.libraryapi.repository.AuthorRepository
import org.springframework.stereotype.Component

@Component
class AuthorValidator(
    private val authorRepository: AuthorRepository
) {

    fun validate(author: Author) {
        if (existsAuthorRegistered(author)) {
            throw DuplicatedRegistryException("${author.name} already registered")
        }
    }

    private fun existsAuthorRegistered(author: Author): Boolean {
        return authorRepository.findByNameAndBirthDateAndNationality(
            name = author.name,
            birthDate = author.birthDate,
            nationality = author.nationality
        ).isPresent
    }

}