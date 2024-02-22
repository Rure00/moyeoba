package com.moyeoba.project.token;

import com.moyeoba.project.token.data.TokenPair;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface TokenManager {
    String resolveToken(HttpServletRequest request);
    TokenPair generateTokens(Long id);
    Boolean validateToken(String tokenStr);
    Authentication getAuthentication(String token);
    String getUserIdFromToken(String tokenStr);
}
