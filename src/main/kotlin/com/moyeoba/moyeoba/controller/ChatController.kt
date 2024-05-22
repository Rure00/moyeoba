package com.moyeoba.moyeoba.controller


import com.moyeoba.moyeoba.data.chat.UserMessage
import com.moyeoba.moyeoba.firbase.FcmManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller


@Controller
class ChatController {
    //@Autowired
    //private lateinit var chatService: ChatService
    @Autowired
    private lateinit var simpleMessage: SimpMessageSendingOperations
    private val  fcmManager = FcmManager()

    @MessageMapping("/hello") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    //@SendTo("/sub/hello") //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    fun chat(userMessage: UserMessage): UserMessage {

        //TODO
        // - Room Entity 생성
        // - Message 에 roomId 넣기
        // - 캐시 이용: https://wans1027.tistory.com/23


        val roomId = 0L
        //채팅 저장
        println("Get Message) $userMessage")
        simpleMessage.convertAndSend("/sub/${roomId}", Json.encodeToString(userMessage))
        return userMessage
    }
}