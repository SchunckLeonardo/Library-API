package io.github.libraryapi.repository

import io.github.libraryapi.model.Book
import io.github.libraryapi.model.BookGenre
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID
import kotlin.test.Test

@SpringBootTest
private class BookRepositoryTest {

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @Test
    fun saveTest() {
        val book = Book()
        book.isbn = "4124120-4124"
        book.price = BigDecimal.valueOf(19.90)
        book.genre = BookGenre.FICTION
        book.title = "The Great Gatsby"
        book.publishedDate = LocalDate.of(1925, 4, 10)

        val author = authorRepository
            .findById(UUID.fromString("6f75e95e-46b8-4ae7-a051-cb7e97f1886e"))
            .orElse(null)

        book.author = author

        bookRepository.save(book)
    }

}