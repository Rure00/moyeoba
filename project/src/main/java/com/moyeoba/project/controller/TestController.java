package com.moyeoba.project.controller;


import com.moyeoba.project.data.entity.User;
import com.moyeoba.project.repository.UserRepository;
import com.moyeoba.project.token.TokenManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    private final TokenManager tokenManager;
    private final UserRepository userRepository;

    public TestController(TokenManager tokenManager, UserRepository userRepository) {
        this.tokenManager = tokenManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/print")
    public ResponseEntity<?> printSome(HttpServletRequest request) {
        System.out.println("TestController");
        return null;
    }

    @PostMapping("/cookie")
    public ResponseEntity<?> cookieTest(HttpServletResponse response) {
        log.info("Generating Cookie...");

        ResponseCookie accessCookie = ResponseCookie.from("access_token", "1234")
                .domain("localhost")    //TODO: "moyeoba.com" 로 바꾸기
                .path("/test/cookie")
                .httpOnly(false)
                .secure(false)
                .maxAge(60 * 30)
                .sameSite("Strict")
                .build();
        ResponseCookie refreshCookie = ResponseCookie.from("access_token", "5678")
                .domain("localhost")    //TODO: "moyeoba.com" 로 바꾸기
                .path("/test/cookie")
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
    }


    @PostMapping("/header/param")
    public ResponseEntity<?> checkHeaderWithParam(@RequestHeader("Content-Type") String contentType) {
        System.out.println("TestController) " + contentType);
        return null;
    }

    @PostMapping("/header/servlet")
    public ResponseEntity<?> checkHeaderWithServlet(HttpServletRequest request) {
        System.out.println("TestController) " + request.getHeader("Content-Type"));
        return null;
    }

    @GetMapping("/token/generate")
    public ResponseEntity<String> generateToken(@PathVariable(name="id") String id) {
        if(id == null) {
            System.out.println("It's null");
        }

        System.out.println("Hello.");

        String token = tokenManager.generateTokens(Long.getLong(id)).getAccessToken();
        System.out.println(token);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
