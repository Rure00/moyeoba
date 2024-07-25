package com.moyeoba.moyeoba.web_socket

import com.moyeoba.moyeoba.jwt.TokenManager
import com.moyeoba.moyeoba.security.WebSocketInterceptor
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer


private val logger = KotlinLogging.logger {}

@EnableWebSocketMessageBroker
@Configuration
class StompWebSocketConfig: WebSocketMessageBrokerConfigurer {

    @Autowired
    private lateinit var tokenManager: TokenManager
    @Autowired
    private lateinit var httpSessionHandshakeInterceptor: MyHandShakeInterceptor

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/web/chat") // 여기로 웹소켓 생성
            .setAllowedOriginPatterns("*")
            .setHandshakeHandler(WebSocketInterceptor())
            .withSockJS()
            .setInterceptors(httpSessionHandshakeInterceptor)
    }


    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        // 메시지를 발행하는 요청 url -> 메시지를 보낼 때
        registry.setApplicationDestinationPrefixes("/pub") // 구독자 -> 서버(메세지보낼때)
        // 메시지를 구독하는 요청 url -> 메시지를 받을 때
        registry.enableSimpleBroker("/sub") // 브로커 -> 구독자들(메세지받을때)
    }



}

