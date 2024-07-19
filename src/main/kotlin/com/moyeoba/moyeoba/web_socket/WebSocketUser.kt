package com.moyeoba.moyeoba.web_socket

import java.security.Principal

data class WebSocketUser(val userId: String): Principal {
    override fun getName(): String {
        return userId
    }
}
