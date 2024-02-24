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

    @GetMapping("/naver")
    public ResponseEntity<TokenPair> naverLogin(@RequestParam("code") String code, @RequestParam("state") String state) {
        log.info("Try Naver Login) {}", code);
        TokenPair tokenPair = naverService.naverLogin(code, state);

        if(tokenPair == null) {
            log.info("Naver Login is Failed) {}", code);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            log.info("Naver Login is success) {}", code);
            return new ResponseEntity<>(tokenPair, HttpStatus.OK);
        }
    }
    @GetMapping("/kakao")
    public ResponseEntity<TokenPair> kakaoLogin(@RequestParam("code") String code) {
        log.info("Try Kakao Login) {}", code);
        TokenPair tokenPair = kakaoService.kakaoLogin(code);

        if(tokenPair == null) {
            log.info("Kakao Login is Failed) {}", code);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            log.info("Kakao Login is success) {}", code);
            return new ResponseEntity<>(tokenPair, HttpStatus.OK);
        }
    }


}
