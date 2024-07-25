package com.moyeoba.moyeoba.controller


import com.moyeoba.moyeoba.data.chat.RawMessage
import com.moyeoba.moyeoba.data.dto.response.ChatResponseDto
import com.moyeoba.moyeoba.firbase.FireBaseManager
import com.moyeoba.moyeoba.jwt.TokenManager
import com.moyeoba.moyeoba.service.ChatService
import com.moyeoba.moyeoba.web_socket.session.SessionUser
import com.moyeoba.moyeoba.web_socket.session.SessionUserEncryption
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller
import java.security.Principal
import java.time.LocalDateTime


@Controller
class WebSocketController {
    @Autowired
    private lateinit var chatService: ChatService
    @Autowired
    private lateinit var simpleMessage: SimpMessageSendingOperations
    @Autowired
    private lateinit var sessionUserEncryption: SessionUserEncryption
    private val tokenManager = TokenManager()
    private val  firebaseManager = FireBaseManager()

    @MessageMapping("/chat") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    fun chat(rawMessage: RawMessage, principal: Principal): ChatResponseDto {

        val sessionUser = principal as SessionUser

        val dto = chatService.addChat(rawMessage, sessionUser.userId)
        val roomId = dto.roomId
        firebaseManager.sendToTopic(
            rawMessage.topicUrl, dto
        )

        simpleMessage.convertAndSend("/sub/chat/${roomId}", Json.encodeToString(dto))

        return dto
    }
}