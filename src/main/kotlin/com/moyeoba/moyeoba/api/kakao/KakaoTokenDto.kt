package com.moyeoba.moyeoba.api.kakao

data class KakaoTokenDto(
        var token_type: String,
        var access_token: String,
        var expires_in: Int,
        var refresh_token: String,
        var refresh_token_expires_in: Int,
)
