package io.github.libraryapi.security

import io.github.libraryapi.model.User
import io.github.libraryapi.service.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class SecurityService(
    private val userService: UserService
) {

    fun getUserSigned(): User? =
        when (val authentication = SecurityContextHolder.getContext().authentication) {
            is CustomAuthentication -> {
                authentication.principal as User
            }
            else -> null
        }

}