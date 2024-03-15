package com.moyeoba.moyeoba.security.cookie

import org.springframework.http.ResponseCookie

data class CookiesData(
        val accessToken: ResponseCookie,
        val refreshToken: ResponseCookie
)
