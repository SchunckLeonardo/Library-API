package io.github.libraryapi.model

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.EqualsAndHashCode
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import lombok.Setter
import lombok.ToString
import java.math.BigDecimal
import java.util.UUID
import java.time.LocalDate

@Entity
@Table(
    name = "books",
    schema = "public"
)
@Data
class Book {

    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: UUID

    @Column(name = "isbn", length = 20, nullable = false)
    lateinit var isbn: String

    @Column(name = "title", length = 150, nullable = false)
    lateinit var title: String

    @Column(name = "published_date")
    lateinit var publishedDate: LocalDate

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", length = 30, nullable = false)
    lateinit var genre: BookGenre

    @Column(name = "price", precision = 18, scale = 2)
    var price: BigDecimal = BigDecimal.ZERO

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "id_author")
    lateinit var author: Author

}