package com.moyeoba.moyeoba.security

import com.moyeoba.moyeoba.web_socket.encrypt.ChatIdEncryption
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.server.ServerHttpRequest
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.support.DefaultHandshakeHandler
import java.security.Principal

class CustomInterceptor: DefaultHandshakeHandler() {

    @Autowired
    private lateinit var chatIdEncryption: ChatIdEncryption

    override fun determineUser(
        request: ServerHttpRequest,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Principal? {
        println("It's a determineUser!")

        return super.determineUser(request, wsHandler, attributes)
    }
}