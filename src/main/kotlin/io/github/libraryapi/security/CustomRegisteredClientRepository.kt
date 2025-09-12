package io.github.libraryapi.security

import io.github.libraryapi.service.ClientService
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import org.springframework.stereotype.Component

@Component
class CustomRegisteredClientRepository(
    private val clientService: ClientService,
    private val tokenSettings: TokenSettings,
    private val clientSettings: ClientSettings
) : RegisteredClientRepository {

    override fun findByClientId(clientId: String?): RegisteredClient? {
        val client = clientId?.let { clientService.getByClientId(it) } ?: return null

        return RegisteredClient
            .withId(client.id?.toString())
            .clientId(client.clientId)
            .clientSecret(client.clientSecret)
            .redirectUri(client.redirectURI)
            .scope(client.scope)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantTypes {
                it.addAll(listOf(
                    AuthorizationGrantType.AUTHORIZATION_CODE,
                    AuthorizationGrantType.CLIENT_CREDENTIALS,
                    AuthorizationGrantType.REFRESH_TOKEN
                ))
            }
            .tokenSettings(tokenSettings)
            .clientSettings(clientSettings)
            .build()
    }

    override fun findById(id: String?): RegisteredClient? {
        TODO("Not yet implemented")
    }

    override fun save(registeredClient: RegisteredClient?) {
        TODO("Not yet implemented")
    }

}