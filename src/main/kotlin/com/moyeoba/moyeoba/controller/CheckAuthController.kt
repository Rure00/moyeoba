package com.moyeoba.moyeoba.controller

import com.moyeoba.moyeoba.api.SocialLoginResult
import com.moyeoba.moyeoba.data.dto.request.LoginRequestDto
import com.moyeoba.moyeoba.data.dto.response.LoginResponse
import com.moyeoba.moyeoba.security.UserDetailsImpl
import com.moyeoba.moyeoba.security.cookie.CookieManager
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class CheckAuthController {

    @Autowired
    private lateinit var cookieManager: CookieManager

    @PostMapping("/login")
    fun login(): ResponseEntity<LoginResponse> {
        val cookiePair = cookieManager.getCookies(1L)
        return ResponseEntity
            .status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, cookiePair.first.toString())
            .header(HttpHeaders.SET_COOKIE, cookiePair.second.toString())
            .body(LoginResponse(true))
    }

    @GetMapping("/check")
    fun checkAuth(request: HttpServletRequest) {
        val cookies = request.cookies
        for (cookie in cookies) {
            println(cookie.name)
        }
    }

    @PostMapping("/get")
    fun get(@AuthenticationPrincipal userDetails: UserDetailsImpl) {
        println("userName is ${userDetails.username}")
    }
}