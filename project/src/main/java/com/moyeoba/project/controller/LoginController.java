package com.moyeoba.project.controller;


import com.moyeoba.project.service.KakaoService;
import com.moyeoba.project.service.NaverService;
import com.moyeoba.project.token.data.TokenPair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/login")
public class LoginController {
    private final NaverService naverService;
    private final KakaoService kakaoService;

    @Autowired
    public LoginController(NaverService naverService, KakaoService kakaoService) {
        this.naverService = naverService;
        this.kakaoService = kakaoService;
    }

    @GetMapping("/naver/code")
    public ResponseEntity<TokenPair> naverCodeLogin(@RequestParam("code") String code, @RequestParam("state") String state) {
        log.info("Try Naver Code Login) {}", code);
        TokenPair tokenPair = naverService.naverCodeLogin(code, state);

        if(tokenPair == null) {
            log.info("Naver Code Login is Failed) {}", code);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            log.info("Naver Code Login is success) {}", code);
            return new ResponseEntity<>(tokenPair, HttpStatus.OK);
        }
    }
    @GetMapping("/naver/token")
    public ResponseEntity<TokenPair> naverTokenLogin(@RequestParam("token") String token) {
        log.info("Try Naver Token Login) {}", token);
        TokenPair tokenPair = naverService.naverTokenLogin(token);

        if(tokenPair == null) {
            log.info("Naver Token Login is Failed) {}", token);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            log.info("Naver Token Login is success) {}", token);
            return new ResponseEntity<>(tokenPair, HttpStatus.OK);
        }
    }

    @GetMapping("/kakao/code")
    public ResponseEntity<TokenPair> kakaoCodeLogin(@RequestParam("code") String code) {
        log.info("Try Kakao Code Login) {}", code);
        TokenPair tokenPair = kakaoService.kakaoCodeLogin(code);

        if(tokenPair == null) {
            log.info("Kakao Code Login is Failed) {}", code);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            log.info("Kakao Code Login is success) {}", code);
            return new ResponseEntity<>(tokenPair, HttpStatus.OK);
        }
    }
    @GetMapping("/kakao/token")
    public ResponseEntity<TokenPair> kakaoTokenLogin(@RequestParam("token") String token) {
        log.info("Try Kakao Token Login) {}", token);
        TokenPair tokenPair = kakaoService.kakaoTokenLogin(token);

        if(tokenPair == null) {
            log.info("Kakao Token Login is Failed) {}", token);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            log.info("Kakao Token Login is success) {}", token);
            return new ResponseEntity<>(tokenPair, HttpStatus.OK);
        }
    }


}
