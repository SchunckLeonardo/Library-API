package io.github.libraryapi.security

import io.github.libraryapi.model.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class CustomAuthentication(
    private val user: User
) : Authentication {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        this.user.roles
            .stream()
            .map { role -> SimpleGrantedAuthority(role) }
            .toList()

    override fun setAuthenticated(isAuthenticated: Boolean) {}

    override fun getName(): String {
        return user.login ?: ""
    }

    override fun getCredentials() {}

    override fun getPrincipal(): Any {
        return user
    }

    override fun isAuthenticated(): Boolean {
        return true
    }

    override fun getDetails(): Any {
        return user
    }

}