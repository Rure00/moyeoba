package com.moyeoba.moyeoba.repository

import com.moyeoba.moyeoba.data.entity.User
import io.hypersistence.utils.spring.repository.HibernateRepository
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByKakaoId(kakaoId: Long): Optional<User>
    fun findByNaverId(naverId: String): Optional<User>

}