package com.moyeoba.moyeoba.controller

import com.moyeoba.moyeoba.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController{

    @Autowired
    private lateinit var userRepository: UserRepository



}