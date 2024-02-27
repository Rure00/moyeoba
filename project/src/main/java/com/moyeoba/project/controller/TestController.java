package com.moyeoba.project.controller;


import com.moyeoba.project.data.entity.User;
import com.moyeoba.project.repository.UserRepository;
import com.moyeoba.project.token.TokenManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("add-mock-user")
    public ResponseEntity<?> testBody() {
        User user = new User(
                0L, "0", "0000", "YnMFaP_UZ-qCd-wquFA4cPB-WwHcMoPi3zFd2BcrOeI", 0L
        ) ;

        userRepository.save(user);
        return new ResponseEntity<>(null, HttpStatus.OK);
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
