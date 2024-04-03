package com.moyeoba.moyeoba.security

import com.moyeoba.moyeoba.jwt.JwtAuthenticationFilter
import com.moyeoba.moyeoba.jwt.TokenManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity(debug = true)
class SecurityConfiguration {

    @Autowired
    private lateinit var tokenManager: TokenManager

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/api-docs/**"
            )
        }
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .csrf { it.disable() }
                .sessionManagement{
                    it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }
                .authorizeHttpRequests {
                    it.requestMatchers("/test/**", "/user/**","/swagger-ui/**", "/v3/**").permitAll()
                            .anyRequest().permitAll()
                }
                .addFilterBefore(JwtAuthenticationFilter(tokenManager), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()!!
    }

}