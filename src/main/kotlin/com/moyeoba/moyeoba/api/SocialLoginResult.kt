package com.moyeoba.moyeoba.api

data class SocialLoginResult(val id: Long, val flag: LoginFlag) {
    enum class LoginFlag {
        NewUser, ExistingUser, Error
    }
}