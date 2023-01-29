package com.example.demo.domain.service.Impl;

import antlr.TokenStreamException;
import com.example.demo.domain.service.TokenRefreshService;
import com.example.demo.global.configuration.security.jwt.TokenProvider;
import com.example.demo.global.exception.ErrorCode;
import com.example.demo.global.exception.error.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenRefreshServiceImpl implements TokenRefreshService {
    private final TokenProvider tokenProvider;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map refresh(String refreshToken) {
        if(tokenProvider.isTokenExpired(refreshToken))
            throw new TokenExpiredException("Refresh token is expired", ErrorCode.TOKEN_EXPIRED);
        String email = tokenProvider.getUserEmail(refreshToken);
        String accessToken = tokenProvider.generateAccessToken(email);
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);
        return response;
    }
}
