package com.moyeoba.moyeoba.web_socket

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry


@Configuration
@EnableWebSocket
class WebSocketConfig: WebSocketConfigurer {

    @Autowired
    private lateinit var webSocketHandler: WebSocketHandler

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        // endpoint 설정 : /api/v1/chat/{postId}
        // 이를 통해서 ws://localhost:9090/ws/chat 으로 요청이 들어오면 websocket 통신을 진행한다.
        // setAllowedOrigins("*")는 모든 ip에서 접속 가능하도록 해줌
        //TODO: permittedpath에 경로 추가하기
        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
    }

}