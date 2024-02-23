package com.moyeoba.project.dao.impl;

import com.moyeoba.project.dao.UserDAO;
import com.moyeoba.project.data.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDAO {
    @Override
    public User getUserById(Integer id) {
        return null;
    }

    @Override
    public User getUserByNaverId(Integer id) {
        return null;
    }

    @Override
    public User getUserByKakaoId(Integer id) {
        return null;
    }
}
