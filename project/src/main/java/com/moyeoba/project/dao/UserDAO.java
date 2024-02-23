package com.moyeoba.project.dao;

import com.moyeoba.project.data.entity.User;

public interface UserDAO {
    User getUserById(Integer id);
    User getUserByNaverId(Integer id);
    User getUserByKakaoId(Integer id);
}
