package com.moyeoba.moyeoba.dao

import com.moyeoba.moyeoba.data.entity.User


interface UserDao {
    fun findUser(userId: Long): User?
    fun findTokens(idList: List<Long>): List<String>
    fun getEmail(userId: Long): String?
    fun saveEmail(userId: Long, email: String): Boolean
    fun getOrCreateKakao(id: Long, nickname: String): User
    fun getOrCreateNaver(id: String, nickname: String): User
}