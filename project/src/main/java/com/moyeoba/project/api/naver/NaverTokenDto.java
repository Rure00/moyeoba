package com.moyeoba.project.api.naver;

import lombok.Data;

@Data
public class NaverTokenDto {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private Integer expires_in;
}
