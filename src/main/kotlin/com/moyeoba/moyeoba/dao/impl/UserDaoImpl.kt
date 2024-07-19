package com.moyeoba.moyeoba.dao.impl

import com.moyeoba.moyeoba.dao.UserDao
import com.moyeoba.moyeoba.data.entity.User
import com.moyeoba.moyeoba.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserDaoImpl: UserDao {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun findUser(userId: Long): User? {
        return userRepository.findById(userId).get()
    }


    override fun findTokens(idList: List<Long>): List<String>
        = idList.map {
            userRepository.findById(it).get().token?: throw Exception("id:$it) Token is Not Registered.")
        }


    override fun getEmail(userId: Long): String?  {
        val user = userRepository.findById(userId)

        if (user.isPresent) {
            return user.get().email
        } else {
            logger.error("UserDaoImpl: getEmail(userId: Long) -> There is no matched userId.")
            return null
        }
    }

    override fun saveEmail(userId: Long, email: String): Boolean {
        val optional = userRepository.findById(userId)
        if(optional.isPresent) {
            val user = optional.get()
            user.email = email

            userRepository.save(user)

            return true
        } else return false
    }

    override fun getOrCreateKakao(id: Long, name: String): User {
        val optional = userRepository.findByKakaoId(id)

        return if (optional.isPresent) optional.get()
        else {
            val newUser = User(
                nickname = name,
                kakaoId = id
            )
            userRepository.save(newUser)
        }
    }


    override fun getOrCreateNaver(id: String, name: String): User {
        val optional = userRepository.findByNaverId(id)

        return if (optional.isPresent) optional.get()
        else {
            val newUser = User(
                nickname = name,
                naverId = id
            )
            userRepository.save(newUser)
        }
    }


}