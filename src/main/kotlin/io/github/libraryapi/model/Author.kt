package io.github.libraryapi.model

import jakarta.persistence.*
import lombok.Getter
import lombok.Setter
import lombok.ToString
import java.util.UUID
import java.time.LocalDate

@Entity
@Table(
    name = "authors",
    schema = "public"
)
@Getter @Setter
@ToString
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

}