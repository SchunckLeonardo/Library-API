package io.github.libraryapi.controller.common

import io.github.libraryapi.controller.dto.FieldError
import io.github.libraryapi.controller.dto.ResponseError
import io.github.libraryapi.exceptions.AuthorNotFoundException
import io.github.libraryapi.exceptions.BookNotFoundException
import io.github.libraryapi.exceptions.DuplicatedRegistryException
import io.github.libraryapi.exceptions.OperationNotAllowedException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGlobalException(
        e: RuntimeException
    ): ResponseError {
        return ResponseError(
            message = e.message ?: "Unexpected error",
            status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        )
    }

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

    @ExceptionHandler(AuthorNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleAuthorNotFoundException(
        e: AuthorNotFoundException
    ): ResponseError {
        return ResponseError(
            message = e.message,
            status = HttpStatus.NOT_FOUND.value()
        )
    }

    @ExceptionHandler(BookNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleBookNotFoundException(
        e: BookNotFoundException
    ): ResponseError {
        return ResponseError(
            message = e.message,
            status = HttpStatus.NOT_FOUND.value()
        )
    }

    @ExceptionHandler(DuplicatedRegistryException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleDuplicatedRegistryException(
        e: DuplicatedRegistryException
    ): ResponseError {
        return ResponseError(
            message = e.message,
            status = HttpStatus.NOT_FOUND.value()
        )
    }

    @ExceptionHandler(OperationNotAllowedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleOperationNotAllowedException(
        e: OperationNotAllowedException
    ): ResponseError {
        return ResponseError(
            message = e.message,
            status = HttpStatus.NOT_FOUND.value()
        )
    }

}