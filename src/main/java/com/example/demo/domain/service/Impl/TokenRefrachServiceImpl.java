package com.example.demo.domain.service.Impl;

import antlr.TokenStreamException;
import com.example.demo.domain.service.TokenRefrachService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenRefrachServiceImpl implements TokenRefrachService {
    private final TokenProvider tokenProvider;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map refresh(String refreshToken){
        if(tokenProvider.isTokenExpired(refreshToken))
            throw new TokenStreamException("Refresh token is expired", ErrorCode.TOKEN_EXPIRED);
        String email = tokenProvider.getUserEmail(refreshToken);
        String accessToken = tokenProvider.generateAccessToken(email);
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);
        return response;
    }
}
