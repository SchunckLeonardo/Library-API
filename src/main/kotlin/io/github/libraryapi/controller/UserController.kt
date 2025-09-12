package io.github.libraryapi.controller

import io.github.libraryapi.controller.dto.UserDTO
import io.github.libraryapi.controller.dto.toUser
import io.github.libraryapi.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(
        @RequestBody userDTO: UserDTO
    ) {
        userService.save(
            userDTO
        )
    }

}