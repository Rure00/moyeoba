package com.moyeoba.moyeoba.service

import com.moyeoba.moyeoba.data.chat.RawMessage
import com.moyeoba.moyeoba.data.dto.response.ChatResponseDto

interface ChatService {
    fun addChat(rawMessage: RawMessage): ChatResponseDto
}