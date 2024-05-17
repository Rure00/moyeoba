package com.moyeoba.moyeoba.security

object PermittedPath {
    val OPEN_PATH = arrayOf<String>(
        "/swagger-ui/**",
        "/swagger-resources/**",
        "/v3/api-docs/**",
        "/test/**",
        "/user/login",
        "/user/login/**",
        "/user/refresh",
        "/web/chat/**",





        "auth/login",
    )
}