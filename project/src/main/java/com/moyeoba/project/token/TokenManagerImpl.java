package com.moyeoba.project.token;

import com.moyeoba.project.token.data.JwtHeader;
import com.moyeoba.project.token.data.JwtPayload;
import com.moyeoba.project.token.data.JwtSign;
import com.moyeoba.project.token.data.TokenPair;

import java.util.Base64;
import java.util.Base64.Decoder;



public class TokenManagerImpl implements TokenManager {
    //https://velog.io/@hiy7030/Spring-Security-JWT-%EC%83%9D%EC%84%B1#2-%ED%85%8C%EC%8A%A4%ED%8C%85-%EB%A9%94%EC%84%9C%EB%93%9C-%EA%B5%AC%ED%98%84
    //https://hudi.blog/self-made-jwt/

    private final Integer ACCESS_TOKEN_VALID_TIME = 30;         //분 단위
    private final Integer REFRESH_TOKEN_VALID_TIME = 20160;     //2주


    private String generateAccessToken(Long id) {
        //STEP1) Header, Payload 생성
        JwtHeader header = new JwtHeader();
        JwtPayload payload = new JwtPayload(id, ACCESS_TOKEN_VALID_TIME);
        //STEP2) Sign 생성
        JwtSign sign = new JwtSign(header, payload);
        //STEP3) 합치기
        String result = header.toJsonBase64() + "." + payload.toJsonBase64() + "." + sign.toJsonBase64();

        return result;
    }


    private String generateRefreshToken(Long id) {
        //STEP1) Header, Payload 생성
        JwtHeader header = new JwtHeader();
        JwtPayload payload = new JwtPayload(id, REFRESH_TOKEN_VALID_TIME);
        //STEP2) Sign 생성
        JwtSign sign = new JwtSign(header, payload);
        //STEP3) 합치기
        String result = header.toJsonBase64() + "." + payload.toJsonBase64() + "." + sign.toJsonBase64();

        return result;
    }

    @Override
    public TokenPair getTokens(Long id) {
        return new TokenPair(
                generateAccessToken(id), generateRefreshToken(id)
        );
    }

    @Override
    public Boolean certificationToken(String tokenStr) {
        boolean result = false;
        String decoded = new String(Base64.getDecoder().decode(tokenStr));

        String[] jsonArray = decoded.split("\\.");
        JwtHeader header = JwtHeader.getFromJson(jsonArray[0]);
        JwtPayload payload = JwtPayload.getFromJson(jsonArray[1]);
        JwtSign sign = JwtSign.getFromJson(jsonArray[2]);

        return sign.checkToken(header, payload);
    }
}
