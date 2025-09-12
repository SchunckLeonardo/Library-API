package io.github.libraryapi.security

import io.github.libraryapi.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtCustomAuthenticationFilter(
    private val userService: UserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        var authentication = SecurityContextHolder.getContext().authentication

        if (mustConvert(authentication)) {
            val login = authentication.name
            val user = userService.getLogin(login)

            authentication = CustomAuthentication(user)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun mustConvert(authentication: Authentication?): Boolean {
        return authentication != null && authentication is JwtAuthenticationToken
    }

}