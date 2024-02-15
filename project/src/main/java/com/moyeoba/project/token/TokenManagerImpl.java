package com.moyeoba.project.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class TokenManagerImpl implements TokenManager {
    @Value("${jwt_secret_key}")
    private String jwtSecretKey;

    //https://velog.io/@hiy7030/Spring-Security-JWT-%EC%83%9D%EC%84%B1#2-%ED%85%8C%EC%8A%A4%ED%8C%85-%EB%A9%94%EC%84%9C%EB%93%9C-%EA%B5%AC%ED%98%84
    //https://hudi.blog/self-made-jwt/

    @Override
    public String generateAccessToken() {

        return null;
    }

    @Override
    public String generateRefreshToken() {

        return null;
    }
}
