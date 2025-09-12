package io.github.libraryapi.security

import io.github.libraryapi.service.UserService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication {
        val login = authentication?.name
        val passwordTyped = authentication?.credentials.toString()

        val user = login?.let {
            userService.getLogin(it)
        } ?: throw UsernameNotFoundException("User not found")

        val passwordEncrypted = user.password

        val isPasswordMatch = passwordEncoder.matches(passwordTyped, passwordEncrypted)

        if (!isPasswordMatch) {
            throw UsernameNotFoundException("Invalid password")
        }

        return CustomAuthentication(user)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication?.isAssignableFrom(UsernamePasswordAuthenticationToken::class.java) == true
    }

}