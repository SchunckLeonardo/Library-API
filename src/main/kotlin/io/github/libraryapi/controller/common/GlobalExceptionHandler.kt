package io.github.libraryapi.controller.common

import io.github.libraryapi.controller.dto.FieldError
import io.github.libraryapi.controller.dto.ResponseError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun handleMethodArgumentNotValidException(
        e: MethodArgumentNotValidException
    ): ResponseError {
        val errors = e.fieldErrors.map { FieldError(error = it.defaultMessage ?: e.message, field = it.field) }

        return ResponseError(
            message = "Validation failed",
            status = HttpStatus.UNPROCESSABLE_ENTITY.value(),
            errors = errors
        )
    }

}