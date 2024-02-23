package com.moyeoba.project.controller;


import com.moyeoba.project.token.TokenManager;
import com.sun.net.httpserver.HttpsServer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    private final TokenManager tokenManager;

    public TestController(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @PostMapping("/print")
    public ResponseEntity<?> printSome(HttpServletRequest request) {
        System.out.println("TestController");
        return null;
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
