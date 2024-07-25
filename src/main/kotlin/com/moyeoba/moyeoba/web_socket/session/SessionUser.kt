package com.moyeoba.moyeoba.web_socket.session

import java.security.Principal

data class SessionUser(
    val userId: Long,
    val userName: String,
    val userEmail: String
): Principal {
    override fun getName() = userName
}
