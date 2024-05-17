package com.moyeoba.moyeoba.web_socket

import com.moyeoba.moyeoba.jwt.TokenManager
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.tomcat.util.http.parser.Cookie
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.server.HandshakeInterceptor
import org.springframework.web.util.WebUtils
import java.lang.Exception


private val logger = KotlinLogging.logger {}

@EnableWebSocketMessageBroker
@Configuration
class StompWebSocketConfig: WebSocketMessageBrokerConfigurer {

    @Autowired
    private lateinit var tokenManager: TokenManager

    @Bean
    fun httpSessionHandshakeInterceptor(): HandshakeInterceptor {
        return object: HandshakeInterceptor {
            override fun beforeHandshake(
                request: ServerHttpRequest,
                response: ServerHttpResponse,
                wsHandler: WebSocketHandler,
                attributes: MutableMap<String, Any>
            ): Boolean {
                val accessToken = (request as ServletServerHttpRequest)
                    .servletRequest.cookies
                    ?.firstOrNull {
                        it.name == "access_token"
                    }?.value

                if(accessToken.isNullOrEmpty()) {
                    logger.info { "HandShake Interceptor) Token Not Found" }
                    return false
                } else if(!tokenManager.validateToken(accessToken)) {
                    logger.info { "HandShake Interceptor) Invalid Token" }
                    return false
                }

                return true
            }

            override fun afterHandshake(
                request: ServerHttpRequest,
                response: ServerHttpResponse,
                wsHandler: WebSocketHandler,
                exception: Exception?
            ) {
                logger.info { "HandShake Interceptor) Connected." }
            }

        }
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/web/chat") // 여기로 웹소켓 생성
            .setAllowedOriginPatterns("*")
            .withSockJS()
            .setInterceptors(httpSessionHandshakeInterceptor())
    }


    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        // 메시지를 발행하는 요청 url -> 메시지를 보낼 때
        registry.setApplicationDestinationPrefixes("/pub") // 구독자 -> 서버(메세지보낼때)
        // 메시지를 구독하는 요청 url -> 메시지를 받을 때
        registry.enableSimpleBroker("/sub") // 브로커 -> 구독자들(메세지받을때)
    }

}