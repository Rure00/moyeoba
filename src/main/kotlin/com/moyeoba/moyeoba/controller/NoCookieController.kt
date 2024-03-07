package com.moyeoba.moyeoba.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cookie")
class NoCookieController {
    @GetMapping("/check")
    fun check(request: HttpServletRequest) {
        val cookies = request.cookies
        for (cookie in cookies) {
            println(cookie.name)
        }


    }
}