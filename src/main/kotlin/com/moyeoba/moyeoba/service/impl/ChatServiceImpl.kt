package com.moyeoba.moyeoba.service.impl

import com.moyeoba.moyeoba.dao.ChatMessageDao
import com.moyeoba.moyeoba.dao.ChattingRoomDao
import com.moyeoba.moyeoba.data.chat.RawMessage
import com.moyeoba.moyeoba.data.dto.response.ChatResponseDto
import com.moyeoba.moyeoba.data.dto.response.GetMessagesResponse
import com.moyeoba.moyeoba.data.entity.ChattingMessage
import com.moyeoba.moyeoba.service.ChatService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class ChatServiceImpl: ChatService {
    @Autowired
    private lateinit var chattingMessageDAO: ChatMessageDao
    @Autowired
    private lateinit var chattingRoomDao: ChattingRoomDao

    override fun addChat(rawMessage: RawMessage): ChatResponseDto {
        val room = chattingRoomDao.getChattingRoom(rawMessage.roomId)
        val newMsg = ChattingMessage(
            chattingRoomId = room!!,
            userId = rawMessage.userId,
            userName = rawMessage.userName,
            body = rawMessage.body,
        )

        return chattingMessageDAO.save(newMsg)
    }

    override fun getMessages(roomId: Long, page: Int): GetMessagesResponse {
        val messageList = chattingMessageDAO.getMessages(roomId, page)
        return GetMessagesResponse(messageList)
    }
}