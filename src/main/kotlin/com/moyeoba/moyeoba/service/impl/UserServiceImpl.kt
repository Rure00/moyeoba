package com.moyeoba.moyeoba.service.impl

import com.moyeoba.moyeoba.api.SocialLoginResult
import com.moyeoba.moyeoba.dao.UserDao
import com.moyeoba.moyeoba.data.entity.User
import com.moyeoba.moyeoba.jwt.TokenManager
import com.moyeoba.moyeoba.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserService {
    @Autowired
    private lateinit var tokenManager: TokenManager
    @Autowired
    private lateinit var userDao: UserDao


    override fun findUser(userId: Long): User? {
        return userDao.findUser(userId)
    }

    override fun authorize(token: String) {
        //TODO
    }

    override fun saveEmail(userId: String, email: String): Boolean {
        val id = userId.toLong()
        return saveEmail(id, email)
    }
    override fun saveEmail(userId: Long, email: String): Boolean {
        return userDao.saveEmail(userId, email)
    }

    override fun saveToken(id: Long, token: String): Boolean {
        //TODO
        return false
    }


}