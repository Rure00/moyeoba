package com.moyeoba.moyeoba.service

import com.moyeoba.moyeoba.data.chat.RawMessage
import com.moyeoba.moyeoba.data.dto.response.ChatResponseDto
import com.moyeoba.moyeoba.data.dto.response.GetMessagesResponse
import org.springframework.data.domain.Page

interface ChatService {
    fun addChat(rawMessage: RawMessage, userId: Long): ChatResponseDto
    fun getMessages(roomId: Long, page: Int): GetMessagesResponse
}