package com.example.demo.domain.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignIn {
    private String email;
    private String password;
}
