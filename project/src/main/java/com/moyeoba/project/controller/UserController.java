package com.moyeoba.project.controller;

import com.moyeoba.project.token.data.TokenPair;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/hello")
    public ResponseEntity<?> printHello() {
        System.out.println("Hello!");
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
