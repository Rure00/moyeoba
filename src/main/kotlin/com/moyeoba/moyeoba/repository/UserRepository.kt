package com.moyeoba.moyeoba.repository

import com.moyeoba.moyeoba.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
}