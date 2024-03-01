package com.moyeoba.project.controller;

import antlr.Token;
import com.moyeoba.project.data.dto.request.LoginRequestDto;
import com.moyeoba.project.data.dto.request.SignUpDto;
import com.moyeoba.project.service.KakaoService;
import com.moyeoba.project.service.NaverService;
import com.moyeoba.project.service.UserService;
import com.moyeoba.project.token.TokenManager;
import com.moyeoba.project.token.TokenManagerImpl;
import com.moyeoba.project.token.data.TokenPair;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TokenManagerImpl tokenManager;
    private final KakaoService kakaoService;
    private final NaverService naverService;

    @PostMapping("/hello")
    public ResponseEntity<?> printHello() {
        System.out.println("Hello!");
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> integratedLogin(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        log.info("Login");
        String social = loginRequestDto.getSocial();
        String type = loginRequestDto.getType();
        String payload = loginRequestDto.getPayload();

        if(!social.equals("kakao") && !social.equals("naver")) {
            log.info("Login: Wrong parameter Passed.");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        log.info("Try login with {}", type);
        TokenPair tokenPair = null;


        try {
            if(social.equals("kakao")) {
                if(type.equals("token")) tokenPair = kakaoService.kakaoTokenLogin(payload);
                    else tokenPair = kakaoService.kakaoCodeLogin(payload);
            }
            else {
                if(type.equals("token")) tokenPair = naverService.naverTokenLogin(payload);
                else tokenPair = naverService.naverCodeLogin(payload);
            }

            if(tokenPair == null)
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);


            ResponseCookie accessCookie = ResponseCookie.from("access_token", tokenPair.getAccessToken())
                            .domain("/")    //TODO: "moyeoba.com" 로 바꾸기
                            .path("/user/login")
                            .httpOnly(false)
                            .secure(false)
                            .maxAge(60 * 30)
                            .sameSite("Strict")
                    .build();
            ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", tokenPair.getRefreshToken())
                        .domain("api.moyeoba.com/user/refresh")
                        .path("/user/login")
                        .httpOnly(false)
                        .secure(false)
                        .maxAge(60 * 60 * 24 * 14)
                        .sameSite("Strict")
                    .build();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                    .body("Success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Boolean> refreshToken(@CookieValue("access_token") String accessToken, @CookieValue("refresh_token") String refreshToken) {
        Long userId = Long.getLong(tokenManager.getUserIdFromToken(accessToken));

        TokenPair tokenPair = tokenManager.generateTokens(userId);
        try {
            ResponseCookie accessCookie = ResponseCookie.from("access_token", tokenPair.getAccessToken())
                    .domain("/")    //TODO: "moyeoba.com" 로 바꾸기
                    .path("/user/login")
                    .httpOnly(false)
                    .secure(false)
                    .maxAge(60 * 30)
                    .sameSite("Strict")
                    .build();
            ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", tokenPair.getRefreshToken())
                    .domain("api.moyeoba.com/user/refresh")    //TODO: "moyeoba.com" 로 바꾸기
                    .path("/user/login")
                    .httpOnly(false)
                    .secure(false)
                    .maxAge(60 * 60 * 24 * 14)
                    .sameSite("Strict")
                    .build();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                    .body(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(false, HttpStatus.ACCEPTED);

    }
}
