package com.moyeoba.moyeoba.service

import com.moyeoba.moyeoba.api.SocialLoginResult

interface UserService {
    fun authorize(token: String)
    fun saveEmail(userId: String, email: String): Boolean
    fun saveEmail(userId: Long, email: String): Boolean
}