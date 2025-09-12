package io.github.libraryapi.controller

import io.github.libraryapi.controller.dto.UserDTO
import io.github.libraryapi.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(
        @RequestBody @Valid userDTO: UserDTO
    ) {
        userService.save(
            userDTO
        )
    }

}