package com.moyeoba.moyeoba.data.dto.response

import com.moyeoba.moyeoba.data.entity.ChattingMessage
import java.time.LocalDate
import java.time.LocalDateTime

data class GetMessagesResponse(
    var messages: List<Message>
) {
    data class Message(
        val id: Long,
        val body: String,
        val sender: String,
        val userId: Long,
        val date: LocalDateTime
    ) {
        constructor(chattingMessage: ChattingMessage):
                this(
                    id = chattingMessage.id.id!!,
                    body = chattingMessage.body,
                    sender = chattingMessage.userName,
                    userId = chattingMessage.userId,
                    date = chattingMessage.createdAt!!
                )
    }
}