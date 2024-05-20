package com.moyeoba.moyeoba.web_socket

import com.moyeoba.moyeoba.jwt.TokenManager
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import java.lang.Exception

private val logger = KotlinLogging.logger {}

@Component
class MyHandShakeInterceptor: HandshakeInterceptor {

    @Autowired
    private lateinit var tokenManager: TokenManager

    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
        val accessToken = (request as ServletServerHttpRequest)
            .headers["AccessToken"]
            ?.firstOrNull()

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