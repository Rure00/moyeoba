package com.moyeoba.moyeoba.jwt

import com.moyeoba.moyeoba.jwt.token.TokenPair
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


class JwtAuthenticationFilter: OncePerRequestFilter() {

    @Autowired
    private lateinit var tokenManager: TokenManager

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        println("Access: ${request.requestURI}")
        println("isOpen?: ${PermittedPath.isOpen(request.requestURI)}")
        if (PermittedPath.isOpen(request.requestURI)) {
            filterChain.doFilter(request, response)
            return
        }

        val cookies = request.cookies
        var accessToken: String? = null
        var refreshToken: String? = null

        for(cookie in cookies) {
            if(cookie.name == "access_token") accessToken = cookie.value
            else if(cookie.name == "refresh_token") refreshToken = cookie.value
        }

        if(accessToken == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Token is not Found");
            return
        }

        if(tokenManager.validateToken(accessToken)) {
            val authentication: Authentication = tokenManager.getAuthentication(accessToken)
            SecurityContextHolder.getContext().authentication = authentication

            filterChain.doFilter(request, response)
            return
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
            return
        }
    }
}