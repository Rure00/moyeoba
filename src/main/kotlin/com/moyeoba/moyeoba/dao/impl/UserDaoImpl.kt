package com.moyeoba.moyeoba.dao.impl

import com.moyeoba.moyeoba.dao.UserDao
import com.moyeoba.moyeoba.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserDaoImpl: UserDao {
    @Autowired
    private lateinit var userRepository: UserRepository

    override fun saveEmail(userId: Long, email: String): Boolean {
        val optional = userRepository.findById(userId)
        if(optional.isPresent) {
            val user = optional.get()

            //TODO: update user entity
            //userRepository.save(user)

            return true
        } else return false
    }
}