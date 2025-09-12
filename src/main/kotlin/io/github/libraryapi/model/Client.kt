package io.github.libraryapi.model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "tb_client")
data class Client(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    @Column(name = "client_id")
    val clientId: String,

    @Column(name = "client_secret")
    var clientSecret: String,

    @Column(name = "redirect_uri")
    val redirectURI: String,

    @Column
    val scope: String? = null

)
