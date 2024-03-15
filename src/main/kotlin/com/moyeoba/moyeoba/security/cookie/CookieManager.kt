package com.moyeoba.moyeoba.security.cookie

import com.moyeoba.moyeoba.jwt.TokenManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component


@Component
class CookieManager {
    @Autowired
    private lateinit var tokenManager: TokenManager

    fun refresh(exRefreshToken: String): CookiesData? {
        if(!tokenManager.validateToken(exRefreshToken))  return null

        val id = tokenManager.getUserIdFromToken(exRefreshToken)

        val generate = tokenManager.generateTokens(id.toLong())

        val accessCookie = makeAccessCookie(generate.accessToken)
        val refreshCookie = makeRefreshCookie(generate.refreshToken)

        return CookiesData(accessCookie, refreshCookie)
    }

    fun getCookies(id: Long): Pair<ResponseCookie, ResponseCookie> {
        val tokenPair = tokenManager.generateTokens(id)

        val accessCookie = makeAccessCookie(tokenPair.accessToken)
        val refreshCookie = makeRefreshCookie(tokenPair.refreshToken)

        return Pair(accessCookie, refreshCookie)
    }

    private fun makeAccessCookie(token: String): ResponseCookie
        = ResponseCookie.from("access_token", token)
            .domain("localhost") //TODO: "moyeoba.com" 로 바꾸기
            .path("/")
            .httpOnly(false)
            .secure(false)      //TODO: true로 바꾸기
            .maxAge(TokenManager.ACCESS_TOKEN_VALID_TIME)
            .sameSite("true")
            .build()

    private fun makeRefreshCookie(token: String): ResponseCookie
        = ResponseCookie.from("refresh_token", token)
            .domain("localhost") //TODO: "moyeoba.com" 로 바꾸기
            .path("/user/refresh")
            .httpOnly(true)
            .secure(false)      //TODO: true로 바꾸기
            .maxAge(TokenManager.REFRESH_TOKEN_VALID_TIME)
            .sameSite("true")
            .build()
}