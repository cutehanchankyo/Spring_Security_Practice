package com.example.demo.domain.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TokenRefreshService {
    Map refrash(String refrashToken);
}
