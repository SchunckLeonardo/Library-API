package io.github.libraryapi.model

import io.github.libraryapi.controller.dto.AuthorDTO
import io.github.libraryapi.controller.dto.GetBookAuthorDTO
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(
    name = "tb_author",
    schema = "public"
)
@EntityListeners(AuditingEntityListener::class)
class Author {

    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @Column(name = "name", length = 100, nullable = false)
    lateinit var name: String

    @Column(name = "birth_date", nullable = false)
    lateinit var birthDate: LocalDate

    @Column(name = "nationality", length = 50, nullable = false)
    lateinit var nationality: String

    @OneToMany(mappedBy = "author")
    var books: MutableList<Book> = mutableListOf()

    @Column(name = "created_at") @CreatedDate
    private lateinit var createdAt: LocalDateTime

    @Column(name = "updated_at") @LastModifiedDate
    private lateinit var updatedAt: LocalDateTime

    @ManyToOne
    @JoinColumn(name = "id_user")
    lateinit var user: User

}

fun Author.toAuthorDTO(): AuthorDTO {
    return AuthorDTO(
        id = this.id,
        name = this.name,
        birthDate = this.birthDate,
        nationality = this.nationality
    )
}

fun Author.toGetBookAuthorDTO(): GetBookAuthorDTO =
    GetBookAuthorDTO(
        name = this.name,
        birthDate = this.birthDate,
        nationality = this.nationality,
    )