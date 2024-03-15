package com.moyeoba.moyeoba.controller

import com.moyeoba.moyeoba.api.SocialLoginResult
import com.moyeoba.moyeoba.api.kakao.KakaoApiManager
import com.moyeoba.moyeoba.api.naver.NaverApiManager
import com.moyeoba.moyeoba.data.dto.request.LoginRequestDto
import com.moyeoba.moyeoba.data.dto.response.LoginResponse
import com.moyeoba.moyeoba.jwt.TokenManager
import com.moyeoba.moyeoba.security.cookie.CookieManager
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
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
    private lateinit var tokenManager: TokenManager
    @Autowired
    private lateinit var kakaoApiManager: KakaoApiManager
    @Autowired
    private lateinit var naverApiManager: NaverApiManager
    @Autowired
    private lateinit var cookieManager: CookieManager

    @PostMapping("/test")
    fun testCookie(request: HttpServletRequest) {
        val cookies = request.cookies
        for (cookie in cookies) {
            println(cookie.name)
        }
    }

    @GetMapping("/refresh")
    fun refresh(request: HttpServletRequest): ResponseEntity<String> {

        val refreshToken = request.cookies.filter {
            it.name == "refresh_token"
        }

        if(refreshToken.isNotEmpty()) {

            cookieManager.refresh(refreshToken[0].value)?.let {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .header(HttpHeaders.SET_COOKIE, it.accessToken.toString())
                        .header(HttpHeaders.SET_COOKIE, it.refreshToken.toString())
                        .body("Success")
            }
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build()
    }

    @GetMapping("/login")
    fun login(@RequestBody loginRequestDto: LoginRequestDto, response: HttpServletResponse): ResponseEntity<LoginResponse> {
        val social = loginRequestDto.social
        val isAuthorized: SocialLoginResult = when(social) {
            "kakao" -> kakaoApiManager.authorize(loginRequestDto.type, loginRequestDto.payload)
            "naver" -> naverApiManager.authorize(loginRequestDto.type, loginRequestDto.payload)
            else -> return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build()
        }

        return when(isAuthorized.flag) {
            SocialLoginResult.LoginFlag.Found -> {
                val cookiePair = cookieManager.getCookies(isAuthorized.id)
                ResponseEntity
                        .status(HttpStatus.OK)
                        .header(HttpHeaders.SET_COOKIE, cookiePair.first.toString())
                        .header(HttpHeaders.SET_COOKIE, cookiePair.second.toString())
                        .body(LoginResponse(true))
            }
            SocialLoginResult.LoginFlag.NotFound ->
                ResponseEntity
                    .status(HttpStatus.OK)
                    .body(LoginResponse(false))

            SocialLoginResult.LoginFlag.Error ->
                ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build()
        }

    }

}