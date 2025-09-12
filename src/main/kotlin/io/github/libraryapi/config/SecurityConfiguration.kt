package io.github.libraryapi.config

import io.github.libraryapi.security.CustomUserDetailsService
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
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
class SecurityConfiguration {

    private final val ENCODER_STRENGHT = 10

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        successHandler: LoginSocialSuccessHandler
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
            .build()

    fun userDetailsService(userService: UserService): UserDetailsService =
        CustomUserDetailsService(userService)

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder(ENCODER_STRENGHT)

    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults =
        GrantedAuthorityDefaults("")

}