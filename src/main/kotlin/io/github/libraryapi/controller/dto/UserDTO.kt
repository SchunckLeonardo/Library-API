package io.github.libraryapi.controller.dto

import io.github.libraryapi.model.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserDTO(
    @field:NotBlank(message = "Login cannot be blank")
    val login: String,

    @field:NotBlank(message = "Login cannot be blank")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Login cannot be blank")
    var password: String?,

    val roles: List<String>
)

fun UserDTO.toUser(): User =
    User(
        login = this.login,
        email = email,
        password = this.password,
        roles = this.roles
    )