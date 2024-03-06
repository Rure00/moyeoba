package com.moyeoba.moyeoba.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/test")
class TestController {

    @GetMapping("/print")
    fun printSome(request: HttpServletRequest?): ResponseEntity<*>? {
        println("TestController")
        return null
    }

}