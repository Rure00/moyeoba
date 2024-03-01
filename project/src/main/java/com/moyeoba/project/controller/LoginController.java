package com.moyeoba.project.controller;


import com.moyeoba.project.data.dto.request.LoginRequestDto;
import com.moyeoba.project.service.KakaoService;
import com.moyeoba.project.service.NaverService;
import com.moyeoba.project.token.data.TokenPair;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    private final NaverService naverService;
    private final KakaoService kakaoService;

    @Autowired
    public LoginController(NaverService naverService, KakaoService kakaoService) {
        this.naverService = naverService;
        this.kakaoService = kakaoService;
    }

    @PostMapping("/-")
    public ResponseEntity<TokenPair> integratedLogin(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        log.info("Login");
        String type = loginRequestDto.getType();
        String token = loginRequestDto.getToken();

        if(!Objects.equals(type, "kakao") && !Objects.equals(type, "naver")) {
            log.info("Login: Wrong parameter Passed.");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        log.info("Try login with {}", type);
        TokenPair tokenPair = null;
        try {
            if(Objects.equals(type, "kakao")) tokenPair = kakaoService.kakaoTokenLogin(token);
            else tokenPair = naverService.naverTokenLogin(token);

            if(tokenPair == null)
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

            Cookie accessCookie = new Cookie("access_token", tokenPair.getAccessToken());
            Cookie refreshCookie = new Cookie("refresh_token", tokenPair.getRefreshToken());

            accessCookie.setPath("/login/-");
            accessCookie.setMaxAge(60 * 30);
            accessCookie.setDomain("localhost");    //TODO: "moyeoba.com" 로 바꾸기

            refreshCookie.setPath("/login/-");
            refreshCookie.setMaxAge(60 * 60 * 24 * 14);
            refreshCookie.setDomain("localhost");       //TODO: "moyeoba.com" 로 바꾸기

            response.addCookie(accessCookie);
            response.addCookie(refreshCookie);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(tokenPair, HttpStatus.OK);
    }
}
