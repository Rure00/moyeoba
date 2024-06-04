package com.moyeoba.moyeoba.data.entity

import com.moyeoba.moyeoba.security.UserRoleEnum
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity @Table(name = "chatting_messages")
class ChatMessage(
    val userId: Long,
    val roomId: Long,
    val body: String,
    val sender: String,
    val date: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}