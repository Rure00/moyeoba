package com.moyeoba.project.service;

import com.moyeoba.project.token.data.TokenPair;

import java.io.UnsupportedEncodingException;

public interface NaverService {
    TokenPair naverCodeLogin(String code);
    TokenPair naverTokenLogin(String token);
}
