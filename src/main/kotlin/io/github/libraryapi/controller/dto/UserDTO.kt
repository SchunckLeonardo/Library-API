package io.github.libraryapi.controller.dto

import io.github.libraryapi.model.User

data class UserDTO(
    val login: String,
    var password: String,
    val roles: List<String>
)

fun UserDTO.toUser(): User =
    User(
        login = this.login,
        password = this.password,
        roles = this.roles
    )