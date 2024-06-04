package com.moyeoba.moyeoba.repository

import com.moyeoba.moyeoba.data.entity.ChatMessage
import com.moyeoba.moyeoba.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRepository: JpaRepository<ChatMessage, Long> {
    fun insert(chatMessage: ChatMessage): ChatMessage
}