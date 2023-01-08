package com.example.demo.domain.service;

import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    void grantRole(Long memberIdx);
}
