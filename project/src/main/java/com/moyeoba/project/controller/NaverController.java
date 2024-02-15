package com.moyeoba.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moyeoba.project.service.NaverService;
import com.moyeoba.project.service.impl.NaverServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/naver")
public class NaverController {
    private final NaverService naverService;

    @Autowired
    public NaverController(NaverService naverService) {
        this.naverService = naverService;
    }


    @GetMapping("/login")
    public ResponseEntity<?> naverLogin(@RequestParam("code") String code, @RequestParam("state") String state) {
        boolean isSuccess = naverService.naverLogin(code, state);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }


}
