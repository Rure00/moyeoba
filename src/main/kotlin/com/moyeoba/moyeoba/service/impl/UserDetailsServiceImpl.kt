package com.moyeoba.moyeoba.service.impl

import com.moyeoba.moyeoba.repository.UserRepository
import com.moyeoba.moyeoba.security.UserDetailsImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl: UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(uid: String): UserDetails? {
        val optional = userRepository.findById(uid.toLong())

        println("loadUserByUserName.isFound : ${optional.isPresent}. user id is ${uid.toLong()}")

        return if(optional.isPresent) {
            UserDetailsImpl(optional.get())
        }  else null
    }

}