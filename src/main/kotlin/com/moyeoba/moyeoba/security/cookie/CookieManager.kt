package com.moyeoba.moyeoba.security.cookie

import com.moyeoba.moyeoba.jwt.TokenManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component


@Component
class CookieManager {
    @Autowired
    private lateinit var tokenManager: TokenManager

    fun refresh(exRefreshToken: String): CookiesData {
        val id = tokenManager.getUserIdFromToken(exRefreshToken)

        val generate = tokenManager.generateTokens(id.toLong())

        val accessCookie = makeAccessCookie(generate.accessToken)
        val refreshCookie = makeRefreshCookie(generate.refreshToken)

        return CookiesData(accessCookie, refreshCookie)
    }

    fun getCookies(id: Long): CookiesData {
        val tokenPair = tokenManager.generateTokens(id)

        val accessCookie = makeAccessCookie(tokenPair.accessToken)
        val refreshCookie = makeRefreshCookie(tokenPair.refreshToken)

        return CookiesData(accessCookie, refreshCookie)
    }

    fun logoutCookies(): CookiesData {
        val accessCookie = makeAccessCookie("", 0)
        val refreshCookie = makeRefreshCookie("", 0)

        return CookiesData(accessCookie, refreshCookie)
    }

    private fun makeAccessCookie(token: String, time: Long = TokenManager.ACCESS_TOKEN_VALID_TIME): ResponseCookie
        = ResponseCookie.from("access_token", token)
            .domain("localhost") //TODO: "moyeoba.com" 로 바꾸기
            .path("/")
            .httpOnly(false)
            .secure(true)      //TODO: true로 바꾸기
            .maxAge(time)
            .sameSite("true")
            .build()

    private fun makeRefreshCookie(token: String, time: Long = TokenManager.REFRESH_TOKEN_VALID_TIME): ResponseCookie
        = ResponseCookie.from("refresh_token", token)
            .domain("localhost") //TODO: "moyeoba.com" 로 바꾸기
            .path("/user/refresh")
            .httpOnly(true)
            .secure(false)      //TODO: true로 바꾸기
            .maxAge(time)
            .sameSite("true")
            .build()
}