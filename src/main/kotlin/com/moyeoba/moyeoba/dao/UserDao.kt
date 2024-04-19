package com.moyeoba.moyeoba.dao

import com.moyeoba.moyeoba.data.entity.User


interface UserDao {
    fun getEmail(userId: Long): String?
    fun saveEmail(userId: Long, email: String): Boolean
    fun getOrCreateKakao(id: Long): User
    fun getOrCreateNaver(id: String): User
}