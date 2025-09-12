package io.github.libraryapi.repository

import io.github.libraryapi.model.Client
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface ClientRepository : JpaRepository<Client, UUID> {

    fun getByClientId(clientId: String): Client

}