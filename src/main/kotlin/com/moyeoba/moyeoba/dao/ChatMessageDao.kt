package com.moyeoba.moyeoba.dao

import com.moyeoba.moyeoba.data.dto.response.ChatResponseDto
import com.moyeoba.moyeoba.data.dto.response.GetMessagesResponse
import com.moyeoba.moyeoba.data.entity.ChattingMessage
import org.springframework.data.domain.Page

interface ChatMessageDao {
    fun save(chatMessage: ChattingMessage): ChatResponseDto
    fun getLastMessageBody(roomId: Long): String
    fun getMessages(roomId: Long, page: Int): List<GetMessagesResponse.Message>
}