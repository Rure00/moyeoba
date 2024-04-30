package com.moyeoba.moyeoba.web_socket

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry


//@Configuration
//@EnableWebSocket
class WebSocketConfig: WebSocketConfigurer {

    @Autowired
    private lateinit var webSocketHandler: WebSocketChatHandler

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry
            .addHandler(webSocketHandler, "/ws/chat/{session_id}")
            .setAllowedOrigins("*");
    }



}