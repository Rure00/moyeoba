package com.moyeoba.project.service;

import com.moyeoba.project.data.dto.request.SignUpDto;

public interface UserService {
    boolean trySignUp(SignUpDto signUpDto);
}
