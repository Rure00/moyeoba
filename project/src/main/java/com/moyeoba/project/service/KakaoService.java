package com.moyeoba.project.service;

import com.moyeoba.project.token.data.TokenPair;

public interface KakaoService {
    TokenPair kakaoCodeLogin(String code);
    TokenPair kakaoTokenLogin(String token);
}
