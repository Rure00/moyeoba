package com.moyeoba.moyeoba.dao

interface UserDao {
    fun saveEmail(userId: Long, email: String): Boolean
}