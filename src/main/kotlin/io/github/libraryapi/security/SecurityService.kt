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

    fun getUserSigned(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails: UserDetails = authentication.principal as UserDetails
        val login = userDetails.username
        return userService.getLogin(login)
    }

}