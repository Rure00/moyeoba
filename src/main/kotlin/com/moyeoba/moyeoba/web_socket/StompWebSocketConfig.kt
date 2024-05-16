package com.moyeoba.moyeoba.web_socket

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer


@EnableWebSocketMessageBroker
@Configuration
class StompWebSocketConfig: WebSocketMessageBrokerConfigurer {

    @Autowired
    private lateinit var inboundInterceptor: InboundInterceptor

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/web/chat") // 여기로 웹소켓 생성
            .setAllowedOriginPatterns("*")
            //.withSockJS()
    }


    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        // 메시지를 발행하는 요청 url -> 메시지를 보낼 때
        registry.setApplicationDestinationPrefixes("/pub") // 구독자 -> 서버(메세지보낼때)
        // 메시지를 구독하는 요청 url -> 메시지를 받을 때
        registry.enableSimpleBroker("/sub") // 브로커 -> 구독자들(메세지받을때)
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        super.configureClientInboundChannel(registration)
        registration.interceptors(inboundInterceptor)
    }



/*
    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        println("Configuring task executor for Client Inbound Channel")
        registration.taskExecutor().corePoolSize(4)
        registration.taskExecutor().maxPoolSize(4)

        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 2
        executor.maxPoolSize = 2
        executor.initialize()

        registration.taskExecutor(executor)

    }

    override fun configureClientOutboundChannel(registration: ChannelRegistration) {
        super.configureClientOutboundChannel(registration)
        registration.taskExecutor().corePoolSize(4)
        registration.taskExecutor().maxPoolSize(4)
    }

 */

}