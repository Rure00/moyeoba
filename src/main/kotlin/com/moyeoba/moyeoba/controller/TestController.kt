package com.moyeoba.moyeoba.controller

import com.moyeoba.moyeoba.jwt.TokenManager
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/test")
class TestController {

    @Autowired
    private lateinit var tokenManager: TokenManager

    @GetMapping("/print")
    fun printSome(request: HttpServletRequest?): ResponseEntity<*>? {
        println("TestController")
        return null
    }

    @PostMapping("/cookie")
    fun getCookies(response: HttpServletResponse): ResponseEntity<String> {
        val accessCookie = ResponseCookie.from("access_token", "1234")
                .domain("localhost") //TODO: "moyeoba.com" 로 바꾸기
                .path("/")
                .httpOnly(false)
                .secure(false)
                .maxAge(TokenManager.ACCESS_TOKEN_VALID_TIME)
                .sameSite("Strict")
                .build()

        val refreshCookie = ResponseCookie.from("refresh_token", "5678")
                .domain("localhost") //TODO: "moyeoba.com" 로 바꾸기
                .path("/")
                .httpOnly(false)
                .secure(false)
                .maxAge(TokenManager.REFRESH_TOKEN_VALID_TIME)
                .sameSite("Strict")
                .build()

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body<String>("Success")
    }

    @PostMapping("/pass-cookie")
    fun printCookies(request: HttpServletRequest): ResponseEntity<String> {
        val cookies = request.cookies
        for (cookie in cookies) {
            println(cookie.name)
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body("Success")
    }

    @GetMapping("/refresh")
    fun refresh(request: HttpServletRequest): ResponseEntity<String> {
        val cookies = request.cookies
        var refreshToken: String? = null
        for (cookie in cookies) {
            if(cookie.name == "refresh_token") refreshToken = cookie.value
        }

        refreshToken?.let {
            val id = 12L //tokenManager.getUserIdFromToken(refreshToken)
            val accessToken = tokenManager.generateTokens(id.toLong()).accessToken

            val accessCookie = ResponseCookie.from("access_token", accessToken)
                    .domain("localhost") //TODO: "moyeoba.com" 로 바꾸기
                    .path("/")
                    .httpOnly(false)
                    .secure(false)
                    .maxAge(TokenManager.ACCESS_TOKEN_VALID_TIME)
                    .sameSite("Strict")
                    .build()

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                    .build()
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build()
    }

}