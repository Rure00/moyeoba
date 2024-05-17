package com.moyeoba.moyeoba.web_socket

import com.moyeoba.moyeoba.jwt.TokenManager
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

private val logger = KotlinLogging.logger {}

@Component
class InboundInterceptor(
    private val request: HttpServletRequest
): ChannelInterceptor {

    private val authenticatedUsers = ConcurrentHashMap<String, WebSocketUser>()

    private val tokenManager = TokenManager()

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = StompHeaderAccessor.wrap(message)

        when(accessor.command) {
            StompCommand.CONNECT -> {
                val accessToken = request.cookies.firstOrNull {
                    it.name == "access_token"
                }?.value ?: throw IllegalArgumentException("WebSocket Inbound Interceptor) Token Not Found")

                println("WebSocket Inbound Interceptor) Access Token is found : $accessToken")

                if(tokenManager.validateToken(accessToken)) {
                    val userId = tokenManager.getUserIdFromToken(accessToken)
                    val user = WebSocketUser(userId)
                    accessor.user = user
                    authenticatedUsers[accessor.sessionId!!] = user
                } else throw IllegalArgumentException("WebSocket Inbound Interceptor) Invalid Token")
            }
            StompCommand.SEND -> {

            }
            StompCommand.DISCONNECT -> {

            }
            else -> {}
        }

        return message
    }
}