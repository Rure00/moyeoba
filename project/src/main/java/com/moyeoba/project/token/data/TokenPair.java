package com.moyeoba.project.token.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenPair {
    private String id;
    private String accessToken;
    private String refreshToken;
}
