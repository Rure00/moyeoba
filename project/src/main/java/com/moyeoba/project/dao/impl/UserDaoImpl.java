package com.moyeoba.project.dao.impl;

import com.moyeoba.project.dao.UserDAO;
import com.moyeoba.project.data.entity.User;
import com.moyeoba.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDAO {
    private final UserRepository userRepository;


    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    @Override
    public User getUserByNaverId(String id) {
        return userRepository.getByNaverId(id);
    }

    @Override
    public User getUserByKakaoId(Integer id) {
        return null;
    }
}
