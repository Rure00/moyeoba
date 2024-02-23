package com.moyeoba.project.controller;

import com.moyeoba.project.service.NaverService;
import com.moyeoba.project.token.data.TokenPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/naver")
public class NaverController {
    private final NaverService naverService;

    @Autowired
    public NaverController(NaverService naverService) {
        this.naverService = naverService;
    }

    @GetMapping("/login")
    public ResponseEntity<TokenPair> naverLogin(@RequestParam("code") String code, @RequestParam("state") String state) {
        TokenPair tokenPair = naverService.naverLogin(code, state);

        if(tokenPair == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(tokenPair, HttpStatus.OK);
        }

    }


}
