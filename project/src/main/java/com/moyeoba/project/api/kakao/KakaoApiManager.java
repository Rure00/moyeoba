package com.moyeoba.project.api.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moyeoba.project.api.naver.NaverProfileDto;
import com.moyeoba.project.api.naver.NaverTokenDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoApiManager {
    @Value("${kakao_client_id}")
    private String kakaoClientId;

    @Value("${kakao_client_secret}")
    private String kakaoClientSecret;


    //TODO: URL 고치기
    private final String redirectUrl = "http://localhost:8080/login/callback/kakao";

    public KakaoTokenDto getToken(String code) {
        RestTemplate token_rt = new RestTemplate();

        HttpHeaders kakaoTokenRequestHeaders = new HttpHeaders();
        kakaoTokenRequestHeaders.add("Content-type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        //TODO: 카카오 보안 강화 켜야함.
        //params.add("client_secret", kakaoClientSecret);
        params.add("code", code);
        params.add("redirect_uri", redirectUrl);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, kakaoTokenRequestHeaders);

        ResponseEntity<String> oauthTokenResponse = token_rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        System.out.println(oauthTokenResponse);

        ObjectMapper token_om = new ObjectMapper();
        KakaoTokenDto kakaoTokenDto = null;
        try {
            kakaoTokenDto = token_om.readValue(oauthTokenResponse.getBody(), KakaoTokenDto.class);
        } catch (JsonProcessingException je) {
            je.printStackTrace();
        }

        return kakaoTokenDto;
    }

    public KakaoProfileDto getUserProfile(@NotNull KakaoTokenDto kakaoToken) throws Exception {
        // 토큰을 이용해 정보를 받아올 API 요청을 보낼 로직 작성하기
        RestTemplate profile_rt = new RestTemplate();
        HttpHeaders userDetailReqHeaders = new HttpHeaders();

        userDetailReqHeaders.add("Authorization", "Bearer " + kakaoToken.getAccess_token());
        userDetailReqHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
        HttpEntity<MultiValueMap<String, String>> naverProfileRequest = new HttpEntity<>(userDetailReqHeaders);

        ResponseEntity<String> userDetailResponse = profile_rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                naverProfileRequest,
                String.class
        );
        System.out.println(userDetailResponse);

        ObjectMapper profile_om = new ObjectMapper();
        KakaoProfileDto kakaoProfile = null;
        try {
            kakaoProfile = profile_om.readValue(userDetailResponse.getBody(), KakaoProfileDto.class);
        } catch (JsonProcessingException je) {
            je.printStackTrace();
            throw je;
        }

        return kakaoProfile;
    }
}
