package com.moyeoba.moyeoba.service

import com.moyeoba.moyeoba.api.SocialLoginResult
import com.moyeoba.moyeoba.data.entity.User

interface UserService {
    fun findUser(userId: Long): User?
    fun authorize(token: String)
    fun saveEmail(userId: String, email: String): Boolean
    fun saveEmail(userId: Long, email: String): Boolean
    fun saveToken(id: Long, token: String): Boolean
}