package com.moyeoba.project.controller;

import com.moyeoba.project.service.KakaoService;
import com.moyeoba.project.service.NaverService;
import com.moyeoba.project.token.data.TokenPair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final KakaoService kakaoService;
    private final NaverService naverService;

    @PostMapping("/hello")
    public ResponseEntity<?> printHello() {
        System.out.println("Hello!");
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp() {
        log.info("SignUp: ");

        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
