package com.moyeoba.project.api.naver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
public class NaverApiManager {

    @Value("${naver_client_id}")
    private String naverClientId;

    @Value("${naver_client_secret}")
    private String naverClientSecret;

    public NaverTokenDto getToken(String code, String state) {
        RestTemplate token_rt = new RestTemplate();

        HttpHeaders naverTokenRequestHeaders = new HttpHeaders();
        naverTokenRequestHeaders.add("Content-type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverClientId);
        params.add("client_secret", naverClientSecret);
        params.add("code", code);
        params.add("state", state);

        HttpEntity<MultiValueMap<String, String>> naverTokenRequest =
                new HttpEntity<>(params, naverTokenRequestHeaders);

        ResponseEntity<String> oauthTokenResponse = token_rt.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                naverTokenRequest,
                String.class
        );
        System.out.println(oauthTokenResponse);

        ObjectMapper token_om = new ObjectMapper();
        NaverTokenDto naverToken = null;
        try {
            naverToken = token_om.readValue(oauthTokenResponse.getBody(), NaverTokenDto.class);
        } catch (JsonProcessingException je) {
            je.printStackTrace();
        }

        return naverToken;
    }

    public NaverProfileDto getUserProfile(@NotNull String naverToken) throws Exception {
        // 토큰을 이용해 정보를 받아올 API 요청을 보낼 로직 작성하기
        RestTemplate profile_rt = new RestTemplate();
        HttpHeaders userDetailReqHeaders = new HttpHeaders();
        userDetailReqHeaders.add("Authorization", "Bearer " + naverToken);
        userDetailReqHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
        HttpEntity<MultiValueMap<String, String>> naverProfileRequest = new HttpEntity<>(userDetailReqHeaders);

        ResponseEntity<String> userDetailResponse = profile_rt.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                naverProfileRequest,
                String.class
        );
        System.out.println(userDetailResponse);

        ObjectMapper profile_om = new ObjectMapper();
        NaverProfileDto naverProfile = null;
        try {
            naverProfile = profile_om.readValue(userDetailResponse.getBody(), NaverProfileDto.class);
        } catch (JsonProcessingException je) {
            je.printStackTrace();
            throw je;
        }

        return naverProfile;
    }

}
