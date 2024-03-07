package com.moyeoba.moyeoba.service

import com.moyeoba.moyeoba.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl: UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(uid: String): UserDetails? {
        val optional = userRepository.getByUid(uid)
        return if(optional.isPresent) optional.get()
                else null
    }

}