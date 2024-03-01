package com.moyeoba.project.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class LoginRequestDto {
    String social;
    String type;
    String payload;
}
