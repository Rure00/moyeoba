package com.moyeoba.moyeoba.dao

import com.moyeoba.moyeoba.data.dto.response.ChatResponseDto
import com.moyeoba.moyeoba.data.entity.ChatMessage

interface ChatDao {
    fun saveChat(chatMessage: ChatMessage): ChatResponseDto
}