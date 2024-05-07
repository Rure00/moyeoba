package com.moyeoba.moyeoba.controller

import com.moyeoba.moyeoba.data.dto.Message
import com.moyeoba.moyeoba.service.ChatService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller


@Controller
class ChatController {
    //@Autowired
    //private lateinit var chatService: ChatService
    @Autowired
    private lateinit var simpleMessage: SimpMessageSendingOperations

    @MessageMapping("/hello") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/sub") //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    fun chat(@DestinationVariable roomId: Long?, message: Message): Message? {
        //채팅 저장

        println("Get Message) ${message.sender} to ${message.receiver}: ${message.data}")

        return null
    }
}