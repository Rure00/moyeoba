package com.moyeoba.moyeoba.security

import com.moyeoba.moyeoba.jwt.TokenManager
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter


class JwtAuthenticationFilter(private val tokenManager: TokenManager): OncePerRequestFilter() {


    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        var accessToken: String? = null
        var refreshToken: String? = null

        request.cookies?.let {
            for(cookie in it) {
                if(cookie.name == "access_token") accessToken = cookie.value
                else if(cookie.name == "refresh_token") refreshToken = cookie.value
            }
        }

        if(accessToken == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Token is not Found");
            return
        }

        if(tokenManager.validateToken(accessToken!!)) {
            val authentication = tokenManager.getAuthentication(accessToken!!)

            if(authentication != null) {
                SecurityContextHolder.getContext().authentication = authentication
                filterChain.doFilter(request, response)
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Token is not Valid");
                return
            }

        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
        }
    }
}