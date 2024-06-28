package com.moyeoba.moyeoba.data.dto.request

data class LoginRequestDto(
        val nickname: String,
        val social: String,
        val type: String,
        val payload: String
)
