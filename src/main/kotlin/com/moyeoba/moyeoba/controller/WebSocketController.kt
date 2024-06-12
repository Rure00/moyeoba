package com.moyeoba.moyeoba.controller


import com.moyeoba.moyeoba.data.chat.RawMessage
import com.moyeoba.moyeoba.data.dto.response.ChatResponseDto
import com.moyeoba.moyeoba.firbase.FireBaseManager
import com.moyeoba.moyeoba.service.ChatService
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller


@Controller
class WebSocketController {
    @Autowired
    private lateinit var chatService: ChatService
    @Autowired
    private lateinit var simpleMessage: SimpMessageSendingOperations
    private val  firebaseManager = FireBaseManager()

    @MessageMapping("/chat") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    fun chat(rawMessage: RawMessage): ChatResponseDto {
        val dto = chatService.addChat(rawMessage)
        val roomId = dto.roomId

        firebaseManager.sendToTopic(
            rawMessage.topicUrl, dto
        )

        simpleMessage.convertAndSend("/sub/${roomId}", Json.encodeToString(dto))

        return dto
    }
}