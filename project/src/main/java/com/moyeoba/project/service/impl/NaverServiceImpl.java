package com.moyeoba.project.service.impl;

import com.moyeoba.project.api.naver.NaverApiManager;
import com.moyeoba.project.dao.UserDAO;
import com.moyeoba.project.data.entity.User;
import com.moyeoba.project.data.token.naver.NaverProfileDto;
import com.moyeoba.project.data.token.naver.NaverTokenDto;
import com.moyeoba.project.service.NaverService;
import com.moyeoba.project.token.TokenManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NaverServiceImpl implements NaverService {

    private final NaverApiManager naverApiManager;
    private final UserDAO userDAO;

    private TokenManagerImpl tokenManagerImpl;


    @Autowired
    public NaverServiceImpl(NaverApiManager naverApiManager, UserDAO userDAO) {
        this.naverApiManager = naverApiManager;
        this.userDAO = userDAO;
        tokenManagerImpl = new TokenManagerImpl();
    }


    @Override
    public boolean naverLogin(String code, String state) {
        NaverTokenDto token = naverApiManager.getToken(code, state);

        try {
            NaverProfileDto profileDto = naverApiManager.getUserProfile(token);

            Integer id = Integer.getInteger(profileDto.getResponse().getId());
            User user = userDAO.getUserByNaverId(id);
            if(user == null) {
                return false;
            }

            String accessToken = tokenManagerImpl.generateAccessToken();
            String refreshToken = tokenManagerImpl.generateRefreshToken();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
