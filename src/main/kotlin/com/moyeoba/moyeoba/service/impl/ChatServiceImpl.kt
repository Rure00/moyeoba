package com.moyeoba.moyeoba.service.impl

import com.moyeoba.moyeoba.dao.ChatDao
import com.moyeoba.moyeoba.data.chat.RawMessage
import com.moyeoba.moyeoba.data.dto.response.ChatResponseDto
import com.moyeoba.moyeoba.data.entity.ChatMessage
import com.moyeoba.moyeoba.service.ChatService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ChatServiceImpl: ChatService {
    @Autowired
    private lateinit var chatDAO: ChatDao


    override fun addChat(rawMessage: RawMessage): ChatResponseDto {
        val newMsg = ChatMessage(
            userId = rawMessage.userId,
            roomId = rawMessage.roomId,
            body = rawMessage.body,
            sender = rawMessage.sender,
            date = LocalDateTime.parse(rawMessage.date)
        )

        return chatDAO.saveChat(newMsg)
    }
}