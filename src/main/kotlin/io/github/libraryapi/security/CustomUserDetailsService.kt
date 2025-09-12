package io.github.libraryapi.security

import io.github.libraryapi.model.User
import io.github.libraryapi.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class CustomUserDetailsService(
    private val userService: UserService
) : UserDetailsService {

    override fun loadUserByUsername(login: String?): UserDetails {
        val user: User = userService.getLogin(login ?: throw IllegalArgumentException("Login cannot be null"))

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.login)
            .password(user.password)
            .roles(*user.roles.toTypedArray())
            .build()
    }

}