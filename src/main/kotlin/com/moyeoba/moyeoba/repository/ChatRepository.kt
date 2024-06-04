package com.moyeoba.moyeoba.repository

import com.moyeoba.moyeoba.data.entity.ChatMessage
import com.moyeoba.moyeoba.data.entity.User
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRepository {
    fun insert(chatMessage: ChatMessage): ChatMessage
}