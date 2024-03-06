package com.moyeoba.moyeoba.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .csrf { it.disable() }
                .sessionManagement{
                    it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }
                .authorizeHttpRequests {
                    it.requestMatchers("/test/**", "/swagger-ui/**", "/v3/**").permitAll()
                            .anyRequest().authenticated()
                }

        return http.build()!!
    }

}