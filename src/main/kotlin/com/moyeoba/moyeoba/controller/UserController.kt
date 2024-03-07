package com.moyeoba.moyeoba.controller

import com.moyeoba.moyeoba.data.dto.request.LoginRequestDto
import com.moyeoba.moyeoba.data.dto.response.LoginResponse
import com.moyeoba.moyeoba.jwt.TokenManager
import com.moyeoba.moyeoba.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController{

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var tokenManager: TokenManager

    @PostMapping("/test")
    fun testCookie(request: HttpServletRequest) {
        val cookies = request.cookies
        for (cookie in cookies) {
            println(cookie.name)
        }
    }

    @GetMapping("/refresh")
    fun refresh(request: HttpServletRequest): ResponseEntity<String> {
        val cookies = request.cookies
        var refreshToken: String? = null
        for (cookie in cookies) {
            if(cookie.name == "refresh_token") refreshToken = cookie.value
        }

        refreshToken?.let {
            val id = tokenManager.getUserIdFromToken(refreshToken)
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

    @GetMapping("/login")
    fun login(@RequestBody loginRequestDto: LoginRequestDto): ResponseEntity<LoginResponse> {
        val social = loginRequestDto.social
        val tokenPair = when(social) {
            "kakao" -> {

            }
            "naver" -> {

            }
            else -> {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .build()
            }


        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build()
    }



}