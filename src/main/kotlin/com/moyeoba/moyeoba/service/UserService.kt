package com.moyeoba.moyeoba.service

interface UserService {
    fun saveEmail(userId: String, email: String): Boolean
    fun saveEmail(userId: Long, email: String): Boolean
}