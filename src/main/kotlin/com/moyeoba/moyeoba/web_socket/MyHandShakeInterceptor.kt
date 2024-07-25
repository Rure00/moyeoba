package com.moyeoba.moyeoba.web_socket

import com.moyeoba.moyeoba.jwt.TokenManager
import com.moyeoba.moyeoba.service.UserService
import com.moyeoba.moyeoba.web_socket.session.SessionUser
import com.moyeoba.moyeoba.web_socket.session.SessionUserEncryption
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import org.springframework.web.socket.server.support.DefaultHandshakeHandler
import java.security.Principal
import java.util.logging.Logger
import kotlin.random.Random


private val logger = KotlinLogging.logger {}

@Component
class MyHandShakeInterceptor: HandshakeInterceptor {

    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var tokenManager: TokenManager
    @Autowired
    private lateinit var sessionUserEncryption: SessionUserEncryption


    /*TODO
            - make it inherit DefaultHandShakeHandler
            - on fun: determineUser, Generate uuId using userId and nickname
            - add Map(uuId, User) to attribute
            - on WebsocketController, access to attribute and get Map using uuid included in a RawMessage.
         */

    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
        println("It's a beforeHandshake!!")

        var accessToken = ""
        var refreshToken = ""
        (request as ServletServerHttpRequest).servletRequest.cookies?.let {
            println("--------------------------------------------------------------")
            for(cookie in it) {
                println("${cookie.name}: ${cookie.value}")
                if(cookie.name == "access_token") accessToken = cookie.value
                else if(cookie.name == "refresh_token") refreshToken = cookie.value
            }
            println("--------------------------------------------------------------")
        }

        //TODO: 테스트 끝나면 활성화
        if(accessToken.isEmpty()) {
            logger.info { "HandShake Interceptor) Token Not Found" }
            //return false
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