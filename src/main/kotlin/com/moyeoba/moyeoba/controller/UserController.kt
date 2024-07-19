package com.moyeoba.moyeoba.controller

import com.moyeoba.moyeoba.api.SocialLoginResult
import com.moyeoba.moyeoba.api.kakao.KakaoApiManager
import com.moyeoba.moyeoba.api.naver.NaverApiManager
import com.moyeoba.moyeoba.data.dto.request.LoginRequestDto
import com.moyeoba.moyeoba.data.dto.request.RegisterEmailDto
import com.moyeoba.moyeoba.data.dto.request.RegisterTokenDto
import com.moyeoba.moyeoba.data.dto.response.LoginResponse
import com.moyeoba.moyeoba.jwt.TokenManager
import com.moyeoba.moyeoba.security.UserDetailsImpl
import com.moyeoba.moyeoba.security.cookie.CookieManager
import com.moyeoba.moyeoba.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
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
    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/login")
    fun login(@RequestBody loginRequestDto: LoginRequestDto, response: HttpServletResponse): ResponseEntity<LoginResponse> {
        val social = loginRequestDto.social
        val isAuthorized: SocialLoginResult = when(social) {
            "kakao" -> kakaoApiManager.authorize(loginRequestDto.type, loginRequestDto.payload, loginRequestDto.nickname)
            "naver" -> naverApiManager.authorize(loginRequestDto.type, loginRequestDto.payload, loginRequestDto.nickname)
            else -> return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build()
        }

        return when(isAuthorized.flag) {
            SocialLoginResult.LoginFlag.ExistingUser -> {
                val cookiePair = cookieManager.getCookies(isAuthorized.id)
                ResponseEntity
                        .status(HttpStatus.OK)
                        .header(HttpHeaders.SET_COOKIE, cookiePair.accessToken.toString())
                        .header(HttpHeaders.SET_COOKIE, cookiePair.refreshToken.toString())
                        .body(LoginResponse(false))
            }
            SocialLoginResult.LoginFlag.NewUser -> {
                val cookiePair = cookieManager.getCookies(isAuthorized.id)
                ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, cookiePair.accessToken.toString())
                    .header(HttpHeaders.SET_COOKIE, cookiePair.refreshToken.toString())
                    .body(LoginResponse(true))
            }
            SocialLoginResult.LoginFlag.Error ->
                ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build()
        }

    }

    @PostMapping("/login/jwt")
    fun loginWithJwt(@RequestBody loginRequestDto: LoginRequestDto, response: HttpServletResponse): ResponseEntity<LoginResponse> {
        val token = loginRequestDto.payload
        val isValid = tokenManager.validateToken(token)

        if(isValid) {
            val id = tokenManager.getUserIdFromToken(token)
            val newToken = tokenManager.generateTokens(id.toLong())

            return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, newToken.accessToken.toString())
                .header(HttpHeaders.SET_COOKIE, newToken.refreshToken.toString())
                .body(LoginResponse(false))
        } else {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build()
        }
    }

    @GetMapping("/refresh")
    fun refresh(request: HttpServletRequest): ResponseEntity<String> {
        val refreshToken = request.cookies.filter {
            it.name == "refresh_token"
        }

        return if(refreshToken.isNotEmpty() && tokenManager.validateToken(refreshToken[0])) {
            val pair = cookieManager.refresh(refreshToken[0].value)

            ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, pair.accessToken.toString())
                .header(HttpHeaders.SET_COOKIE, pair.refreshToken.toString())
                .body("Success")
        } else {
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build()
        }
    }

    @PostMapping("/register-email")
    fun resisterEmail(@RequestBody registerEmailDto: RegisterEmailDto,
                      @AuthenticationPrincipal userDetails: UserDetailsImpl
    ): ResponseEntity<Boolean> {
        val userId = userDetails.user.id!!

        return if(userService.saveEmail(userId, registerEmailDto.email)) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body(true)
        } else ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(false)
    }

    @GetMapping("/logout")
    fun logout(): ResponseEntity<Boolean> {
        val pair = cookieManager.logoutCookies()

        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, pair.accessToken.toString())
            .header(HttpHeaders.SET_COOKIE, pair.refreshToken.toString())
            .body(true)
    }

    @PostMapping("/token/registration")
    fun resistToken(
        @RequestBody registerTokenDto: RegisterTokenDto,
        @AuthenticationPrincipal userDetails: UserDetailsImpl
    ): ResponseEntity<Boolean>
        = ResponseEntity.status(HttpStatus.OK)
            .body(userService.saveToken(userDetails.user.id!!, registerTokenDto.token))




}