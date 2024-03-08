package com.moyeoba.moyeoba.api.naver

data class NaverTokenDto(
    var access_token: String,
    var refresh_token: String,
    var token_type: String,
    var expires_in: Int,
)
