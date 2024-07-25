package com.moyeoba.moyeoba.security

import com.moyeoba.moyeoba.web_socket.session.SessionUser
import com.moyeoba.moyeoba.web_socket.session.SessionUserEncryption
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.support.DefaultHandshakeHandler
import java.security.Principal
import kotlin.random.Random

class CustomInterceptor: DefaultHandshakeHandler() {

    @Autowired
    private lateinit var sessionUserEncryption: SessionUserEncryption

    override fun determineUser(
        request: ServerHttpRequest,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Principal {
        println("It's a determineUser!")
        var accessToken = ""
        (request as ServletServerHttpRequest).servletRequest.cookies?.let {
            for(cookie in it) {
                if(cookie.name == "access_token") accessToken = cookie.value
            }
        }

        val sessionId = request.servletRequest.session.id
        val random = Random.nextInt(0, Int.MAX_VALUE)

        println("sessionId: $sessionId, random: $random")

        //TODO: 테스트 종료 후 복구
        //val user = userService.findUser(tokenManager.getUserIdFromToken(accessToken).toLong())!!
        //return SessionUser(userId = user.id!!, userName = user.nickname, userEmail = user.email!!)

        //TODO: 테스트 종료 후 삭제
        return SessionUser(1L, "nickname", "email")
    }
}