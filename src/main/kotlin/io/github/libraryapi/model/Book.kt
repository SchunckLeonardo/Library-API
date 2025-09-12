package io.github.libraryapi.model

import io.github.libraryapi.controller.dto.BookDTO
import io.github.libraryapi.controller.dto.GetBookResponseDTO
import io.github.libraryapi.controller.dto.RegisterBookDTO
import io.github.libraryapi.model.enums.BookGenreEnum
import jakarta.persistence.*
import lombok.Data
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.util.UUID
import java.time.LocalDate

@Entity
@Table(
    name = "tb_book",
    schema = "public"
)
@Data
@EntityListeners(AuditingEntityListener::class)
data class Book(

    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(name = "isbn", length = 20, nullable = false)
    var isbn: String? = null,

    @Column(name = "title", length = 150, nullable = false)
    var title: String? = null,

    @Column(name = "published_date")
    var publishedDate: LocalDate? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", length = 30, nullable = false)
    var genre: BookGenreEnum? = null,

    @Column(name = "price", precision = 18, scale = 2)
    var price: BigDecimal = BigDecimal.ZERO,

    @ManyToOne//(cascade = [CascadeType.ALL])
    @JoinColumn(name = "id_author")
    var author: Author? = null,

    @ManyToOne
    @JoinColumn(name = "id_user")
    var user: User

)

fun Book.toRegisterBookDTO(): RegisterBookDTO {
    return RegisterBookDTO(
        isbn = this.isbn,
        title = this.title,
        publishedDate = this.publishedDate,
        genre = this.genre?.name,
        price = this.price,
        authorId = this.author?.id
    )
}

fun Book.toBookDTO(): BookDTO =
    BookDTO(
        id = this.id,
        isbn = this.isbn,
        title = this.title,
        publishedDate = this.publishedDate,
        genre = this.genre,
        price = this.price,
        author = this.author?.toAuthorDTO()
    )

fun Book.toGetBookResponseDTO(): GetBookResponseDTO =
    GetBookResponseDTO(
        id = this.id,
        isbn = this.isbn,
        title = this.title,
        publishedDate = this.publishedDate,
        genre = this.genre,
        price = this.price,
        author = this.author?.toGetBookAuthorDTO()
    )