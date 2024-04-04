package com.moyeoba.moyeoba.service.impl

import com.moyeoba.moyeoba.dao.UserDao
import com.moyeoba.moyeoba.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserService {

    @Autowired
    private lateinit var userDao: UserDao

    override fun saveEmail(userId: String, email: String): Boolean {
        val id = userId.toLong()
        return saveEmail(id, email)
    }
    override fun saveEmail(userId: Long, email: String): Boolean {
        return userDao.saveEmail(userId, email)
    }


}