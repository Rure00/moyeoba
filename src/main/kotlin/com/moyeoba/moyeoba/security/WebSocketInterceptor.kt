package com.moyeoba.moyeoba.security

import com.moyeoba.moyeoba.jwt.TokenManager
import com.moyeoba.moyeoba.service.UserService
import com.moyeoba.moyeoba.web_socket.session.SessionUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.support.DefaultHandshakeHandler
import java.security.Principal
import kotlin.random.Random

class WebSocketInterceptor: DefaultHandshakeHandler() {

    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var tokenManager: TokenManager

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

        if(accessToken.isNotEmpty()) {
            val user = userService.findUser(tokenManager.getUserIdFromToken(accessToken).toLong())!!
            //TODO: nickname!! 으로 바꾸기
            return SessionUser(userId = user.id!!, userName = user.nickname?: "이름없음", userEmail = user.email!!)
        } else {
            //TODO: 테스트 종료 후 삭제
            return SessionUser(0L, "Tester", "email@naver.com")
        }
    }
}