package com.moyeoba.project.service.impl;

import com.moyeoba.project.api.naver.NaverApiManager;
import com.moyeoba.project.dao.UserDAO;
import com.moyeoba.project.data.entity.User;
import com.moyeoba.project.api.naver.NaverProfileDto;
import com.moyeoba.project.api.naver.NaverTokenDto;
import com.moyeoba.project.service.NaverService;
import com.moyeoba.project.token.TokenManager;
import com.moyeoba.project.token.data.TokenPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NaverServiceImpl implements NaverService {

    private final NaverApiManager naverApiManager;
    private final UserDAO userDAO;

    private final TokenManager tokenManager;


    @Autowired
    public NaverServiceImpl(NaverApiManager naverApiManager, UserDAO userDAO, TokenManager tokenManager) {
        this.naverApiManager = naverApiManager;
        this.userDAO = userDAO;
        this.tokenManager = tokenManager;
    }


    @Override
    public TokenPair naverCodeLogin(String code, String state) {
        NaverTokenDto token = naverApiManager.getToken(code, state);

        try {
            NaverProfileDto profileDto = naverApiManager.getUserProfile(token.getAccess_token());
            Integer id = Integer.getInteger(profileDto.getResponse().getId());
            User user = userDAO.getUserByNaverId(id);

            return tokenManager.generateTokens(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public TokenPair naverTokenLogin(String token) {
        try {
            NaverProfileDto profileDto = naverApiManager.getUserProfile(token);
            Integer id = Integer.getInteger(profileDto.getResponse().getId());
            User user = userDAO.getUserByNaverId(id);

            return tokenManager.generateTokens(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
