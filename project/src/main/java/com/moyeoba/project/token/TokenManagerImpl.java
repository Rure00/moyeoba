package com.moyeoba.project.token;

import com.moyeoba.project.service.impl.UserDetailsServiceImpl;
import com.moyeoba.project.token.data.JwtHeader;
import com.moyeoba.project.token.data.JwtPayload;
import com.moyeoba.project.token.data.JwtSign;
import com.moyeoba.project.token.data.TokenPair;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;



@Component
@Slf4j
@RequiredArgsConstructor
public class TokenManagerImpl implements TokenManager {
    //https://velog.io/@hiy7030/Spring-Security-JWT-%EC%83%9D%EC%84%B1#2-%ED%85%8C%EC%8A%A4%ED%8C%85-%EB%A9%94%EC%84%9C%EB%93%9C-%EA%B5%AC%ED%98%84
    //https://hudi.blog/self-made-jwt/

    private final String TOKEN_HEADER_KEY = "Authorization";
    private final UserDetailsService userDetailsService;
    @Value("${jwt_secret_key}")
    private String secretKey;

    private final Integer ACCESS_TOKEN_VALID_TIME = 30;         //분 단위
    private final Integer REFRESH_TOKEN_VALID_TIME = 20160;     //2주



    private String generateAccessToken(Long id) {
        System.out.println("토큰 생성 중...");

        //STEP1) Header, Payload 생성
        JwtHeader header = new JwtHeader();
        JwtPayload payload = new JwtPayload(id, ACCESS_TOKEN_VALID_TIME);
        //STEP2) Sign 생성
        JwtSign sign = new JwtSign(header, payload, secretKey);
        //STEP3) 합치기
        String result = header.toJsonBase64() + "." + payload.toJsonBase64() + "." + sign.toJsonBase64();

        return result;
    }


    private String generateRefreshToken(Long id) {
        //STEP1) Header, Payload 생성
        JwtHeader header = new JwtHeader();
        JwtPayload payload = new JwtPayload(id, REFRESH_TOKEN_VALID_TIME);
        //STEP2) Sign 생성
        JwtSign sign = new JwtSign(header, payload, secretKey);
        //STEP3) 합치기
        String result = header.toJsonBase64() + "." + payload.toJsonBase64() + "." + sign.toJsonBase64();

        return result;
    }



    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserIdFromToken(token));
        if(userDetails==null) {
            String id = getUserIdFromToken(token);
            log.warn("userDetails not found) uid is {}", id);
        }

        assert userDetails != null;
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        //TODO: 바꾸기
        if(request.getHeader(TOKEN_HEADER_KEY) != null ) {
            //return request.getHeader(TOKEN_HEADER_KEY).substring(7);
        }
        //return null;
        return request.getHeader(TOKEN_HEADER_KEY);
    }

    @Override
    public TokenPair generateTokens(Long id) {
        return new TokenPair(
                Long.toString(id), generateAccessToken(id), generateRefreshToken(id)
        );
    }

    @Override
    public Boolean validateToken(String token) {
        String[] jsonArray = token.split("\\.");

        System.out.println("header:  "+ new String(Base64.getDecoder().decode(jsonArray[0])));
        JwtHeader header = JwtHeader.getFromJson(new String(Base64.getDecoder().decode(jsonArray[0])));

        System.out.println("payload:  "+ new String(Base64.getDecoder().decode(jsonArray[1])));
        JwtPayload payload = JwtPayload.getFromJson(new String(Base64.getDecoder().decode(jsonArray[1])));

        System.out.println("sign:  "+ new String(Base64.getDecoder().decode(jsonArray[2])));
        JwtSign sign = JwtSign.getFromJson(new String(Base64.getDecoder().decode(jsonArray[2])));

        return sign.checkToken(header, payload);
    }



    @Override
    public String getUserIdFromToken(String token) {
        String[] jsonArray = token.split("\\.");
        JwtPayload payload = JwtPayload.getFromJson(new String(Base64.getDecoder().decode(jsonArray[1])));

        return payload.getUid();
    }
}
