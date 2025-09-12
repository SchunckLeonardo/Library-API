package io.github.libraryapi.repository

import io.github.libraryapi.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {

    fun findByLogin(login: String): Optional<User>
    fun findByEmail(email: String): Optional<User>

}