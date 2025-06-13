package io.github.libraryapi.controller.dto

import org.springframework.http.HttpStatus

data class ResponseError(val status: Int, val message: String, val errors: List<FieldError>) {

    companion object {
        fun defaultResponse(message: String): ResponseError {
            return ResponseError(
                status = HttpStatus.BAD_REQUEST.value(),
                message = message,
                errors = emptyList()
            )
        }

        fun conflict(message: String): ResponseError {
            return ResponseError(
                status = HttpStatus.CONFLICT.value(),
                message = message,
                errors = emptyList()
            )
        }

    }

}