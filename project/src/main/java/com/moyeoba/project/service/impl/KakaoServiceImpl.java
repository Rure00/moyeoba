package com.moyeoba.project.service.impl;

import com.moyeoba.project.api.kakao.KakaoApiManager;
import com.moyeoba.project.api.kakao.KakaoProfileDto;
import com.moyeoba.project.api.kakao.KakaoTokenDto;
import com.moyeoba.project.api.naver.NaverApiManager;
import com.moyeoba.project.api.naver.NaverProfileDto;
import com.moyeoba.project.api.naver.NaverTokenDto;
import com.moyeoba.project.dao.UserDAO;
import com.moyeoba.project.data.entity.User;
import com.moyeoba.project.service.KakaoService;
import com.moyeoba.project.token.TokenManager;
import com.moyeoba.project.token.data.TokenPair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoServiceImpl implements KakaoService {

    private final KakaoApiManager kakaoApiManager;
    private final UserDAO userDAO;

    private final TokenManager tokenManager;

    @Override
    public TokenPair kakaoCodeLogin(String code) {
        KakaoTokenDto token = kakaoApiManager.getToken(code);

        try {
            KakaoProfileDto profileDto = kakaoApiManager.getUserProfile(token.getAccess_token());
            Integer id = Integer.getInteger(profileDto.getId());
            User user = userDAO.getUserByKakaoId(id);

            return tokenManager.generateTokens(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public TokenPair kakaoTokenLogin(String token) {
        try {
            KakaoProfileDto profileDto = kakaoApiManager.getUserProfile(token);
            Integer id = Integer.getInteger(profileDto.getId());
            User user = userDAO.getUserByKakaoId(id);

            return tokenManager.generateTokens(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
