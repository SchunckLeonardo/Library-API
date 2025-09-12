package io.github.libraryapi.model

import io.hypersistence.utils.hibernate.type.array.ListArrayType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import java.util.UUID

@Entity
@Table(name = "tb_user")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column
    val login: String? = "",

    @Column
    val email: String? = "",

    @Column
    var password: String? = "",

    @Type(ListArrayType::class)
    @Column(
        name = "roles",
        columnDefinition = "varchar[]"
    )
    val roles: List<String> = emptyList()

)
