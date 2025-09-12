package io.github.libraryapi.security

import io.github.libraryapi.controller.dto.UserDTO
import io.github.libraryapi.model.User
import io.github.libraryapi.model.enums.UserRoleEnum
import io.github.libraryapi.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class LoginSocialSuccessHandler(
    private val userService: UserService
) : SavedRequestAwareAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val oauthAuthentication: OAuth2AuthenticationToken = authentication as OAuth2AuthenticationToken
        val oAuth2User = oauthAuthentication.principal

        val email = oAuth2User.getAttribute<String>("email") ?: ""

        val user: User = userService.getByEmail(email).orElseGet {
            userService.save(
                userDTO = UserDTO(
                    email = email,
                    login = email,
                    password = "",
                    roles = listOf(
                        UserRoleEnum.USER.name,
                        UserRoleEnum.ADMIN.name
                    )
                )
            )
        }

        val customAuthentication = CustomAuthentication(user)

        SecurityContextHolder.getContext().authentication = customAuthentication

        super.onAuthenticationSuccess(request, response, customAuthentication)
    }

}