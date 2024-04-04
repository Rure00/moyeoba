package com.moyeoba.moyeoba.jwt.token

data class TokenPair(
        val accessToken: String,
        val refreshToken: String
)
