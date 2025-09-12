package io.github.libraryapi.service

import io.github.libraryapi.controller.dto.UserDTO
import io.github.libraryapi.controller.dto.toUser
import io.github.libraryapi.exceptions.DuplicatedRegistryException
import io.github.libraryapi.model.User
import io.github.libraryapi.repository.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun save(userDTO: UserDTO): User {
        if (userRepository.findByLogin(userDTO.login).isPresent) {
            throw DuplicatedRegistryException("Login ${userDTO.login} already registered")
        }

        val encodedPassword = passwordEncoder.encode(userDTO.password)
        userDTO.password = encodedPassword
        return userRepository.save(userDTO.toUser())
    }

    fun getLogin(login: String): User =
        userRepository.findByLogin(login).orElseThrow { UsernameNotFoundException("Username not found") }

    fun getByEmail(email: String): Optional<User> =
        userRepository.findByEmail(email)

}