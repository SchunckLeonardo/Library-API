package io.github.libraryapi.service

import io.github.libraryapi.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
) {
}