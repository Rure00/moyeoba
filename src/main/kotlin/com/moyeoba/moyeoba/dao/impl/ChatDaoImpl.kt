package com.moyeoba.moyeoba.dao.impl

import com.moyeoba.moyeoba.dao.ChatDao
import com.moyeoba.moyeoba.data.dto.response.ChatResponseDto
import com.moyeoba.moyeoba.data.entity.ChatMessage
import com.moyeoba.moyeoba.repository.ChatRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ChatDaoImpl: ChatDao {
    @Autowired
    private lateinit var chatRepository: ChatRepository

    override fun saveChat(chatMessage: ChatMessage): ChatResponseDto {
        val entity = chatRepository.insert(chatMessage)

        return ChatResponseDto(
            id = entity.id!!,
            roomId = entity.roomId,
            body = entity.body,
            sender = entity.sender,
            date = entity.date.toString()
        )
    }

}