package com.moyeoba.moyeoba.data.dto.request

import javax.annotation.meta.TypeQualifierNickname

data class LoginRequestDto(
        val nickname: String,
        val social: String,
        val type: String,
        val payload: String
)
