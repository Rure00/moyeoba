package com.moyeoba.moyeoba.web_socket

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler


@Component
class WebSocketChatHandler: TextWebSocketHandler() {

    //https://velog.io/@sunkyuj/Spring-%EC%9B%B9%EC%86%8C%EC%BC%93%EC%9C%BC%EB%A1%9C-%EC%8B%A4%EC%8B%9C%EA%B0%84-%EC%B1%84%ED%8C%85-%EA%B5%AC%ED%98%84

    //소켓 연결 확인
    override fun afterConnectionEstablished(session: WebSocketSession) {
        super.afterConnectionEstablished(session)
    }
    
    //소켓 통신 시 메시지 확인
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        super.handleTextMessage(session, message)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        super.afterConnectionClosed(session, status)
    }
}