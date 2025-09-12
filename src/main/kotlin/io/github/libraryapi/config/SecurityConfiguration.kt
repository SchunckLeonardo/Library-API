package io.github.libraryapi.config

import io.github.libraryapi.security.CustomUserDetailsService
import io.github.libraryapi.security.JwtCustomAuthenticationFilter
import io.github.libraryapi.security.LoginSocialSuccessHandler
import io.github.libraryapi.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
class SecurityConfiguration {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        successHandler: LoginSocialSuccessHandler,
        jwtCustomAuthenticationFilter: JwtCustomAuthenticationFilter
    ): SecurityFilterChain =
        http
            .csrf { it.disable() }
            .httpBasic {}
            .oauth2Login { oauth2 ->
                oauth2.successHandler(successHandler)
            }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(HttpMethod.POST, "/users/**").permitAll()
                auth.anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2rs -> oauth2rs.jwt {}}
            .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter::class.java)
            .build()

    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults =
        GrantedAuthorityDefaults("")

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val authoritiesConverter = JwtGrantedAuthoritiesConverter()
        authoritiesConverter.setAuthorityPrefix("")

        val converter = JwtAuthenticationConverter()
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter)

        return converter
    }

}