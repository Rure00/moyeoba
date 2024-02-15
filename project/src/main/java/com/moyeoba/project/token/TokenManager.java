package com.moyeoba.project.token;

public interface TokenManager {
    String generateAccessToken();
    String generateRefreshToken();
}
