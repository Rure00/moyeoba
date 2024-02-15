package com.moyeoba.project.token;

import com.moyeoba.project.token.data.TokenPair;

public interface TokenManager {
    TokenPair getTokens(Long id);
    Boolean certificationToken(String tokenStr);
}
