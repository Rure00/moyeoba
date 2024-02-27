package com.moyeoba.project.dao;

import com.moyeoba.project.data.entity.User;

public interface UserDAO {
    User getUserById(Long id);
    User getUserByNaverId(String id);
    User getUserByKakaoId(Integer id);
}
