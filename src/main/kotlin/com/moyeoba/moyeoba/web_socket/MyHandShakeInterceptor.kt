package com.moyeoba.moyeoba.web_socket

import com.moyeoba.moyeoba.jwt.TokenManager
import com.moyeoba.moyeoba.service.UserService
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
    private lateinit var userService: UserService
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
            //return false
        } else if(!tokenManager.validateToken(accessToken)) {
            logger.info { "HandShake Interceptor) Invalid Token" }
            return false
        }

        return true

        /*TODO
            - make it inherit DefaultHandShakeHandler
            - on fun: determineUser, Generate uuId using userId, roomId and nickname
            - add Map(uuId, User) to attribute
            - on WebsocketController, access to attribute and get Map using uuid included in a RawMessage.
         */

//        val userId = 1L//tokenManager.getUserIdFromToken(accessToken).toLong()
//        val user = userService.findUser(userId)
//        if(user != null) {
//            val userDetails = UserDetailsImpl(user)
//            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
//            SecurityContextHolder.getContext().authentication = authentication
//
//            if (authentication.isAuthenticated) {
//                attributes["user${user.id}"] = authentication.principal;
//            }
//
//            return true
//        } else {
//            myLogger.info { "HandShake Interceptor) Invalid User Id" }
//            return false
//        }
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