package io.github.libraryapi.service

import io.github.libraryapi.model.Client
import io.github.libraryapi.repository.ClientRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class ClientService(
    private val clientRepository: ClientRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun save(client: Client) {
        val encryptedPassword = passwordEncoder.encode(client.clientSecret)
        client.clientSecret = encryptedPassword
        clientRepository.save(client)
    }

    fun getByClientId(clientId: String): Client {
        return clientRepository.getByClientId(clientId)
    }

}