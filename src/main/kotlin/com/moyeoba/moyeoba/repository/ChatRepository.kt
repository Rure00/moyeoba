package com.moyeoba.moyeoba.repository

import com.moyeoba.moyeoba.data.entity.ChatMessage
import com.moyeoba.moyeoba.data.entity.User
import java.util.*

interface ChatRepository {
    fun insert(chatMessage: ChatMessage): ChatMessage
}