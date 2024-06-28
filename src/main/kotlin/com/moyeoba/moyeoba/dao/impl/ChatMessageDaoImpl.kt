package com.moyeoba.moyeoba.dao.impl

import com.moyeoba.moyeoba.dao.ChatMessageDao
import com.moyeoba.moyeoba.data.dto.response.ChatResponseDto
import com.moyeoba.moyeoba.data.dto.response.GetMessagesResponse
import com.moyeoba.moyeoba.data.entity.ChattingMessage
import com.moyeoba.moyeoba.repository.ChatMessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class ChatMessageDaoImpl: ChatMessageDao {
    @Autowired
    private lateinit var chatMessageRepository: ChatMessageRepository
    override fun save(chatMessage: ChattingMessage): ChatResponseDto {
        val saved = chatMessageRepository.save(chatMessage)

        return ChatResponseDto(
            id = saved.id.id!!,
            roomId = saved.id.chattingRoomId,
            body = saved.body,
            sender = saved.userName,
            date = saved.createdAt.toString()
        )
    }

    override fun getLastMessageBody(roomId: Long): String
        = chatMessageRepository.findTopByIdChattingRoomIdOrderByCreatedAt(roomId).get().body ?: ""

    override fun getMessages(roomId: Long, page: Int): List<GetMessagesResponse.Message> {
        val pageSize = 50
        val pageable = PageRequest.of(page, pageSize)
        val pageResult = chatMessageRepository.findByIdChattingRoomIdOrderByCreatedAt(roomId, pageable)

        return pageResult.map {
            GetMessagesResponse.Message(it)
        }.toList()
    }

}