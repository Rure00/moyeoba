package com.moyeoba.moyeoba.repository

import com.moyeoba.moyeoba.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, Long> {
    fun findByUid(uid: String): Optional<User>
    fun findByKakaoId(kakaoId: Long): Optional<User>
    fun findByNaverId(naverId: String): Optional<User>
}