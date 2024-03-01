package com.moyeoba.project.service.impl;

import com.moyeoba.project.data.dto.request.SignUpDto;
import com.moyeoba.project.data.entity.User;
import com.moyeoba.project.repository.UserRepository;
import com.moyeoba.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public boolean trySignUp(SignUpDto signUpDto) {
        User user = new User(signUpDto.getPhoneNumber());
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
