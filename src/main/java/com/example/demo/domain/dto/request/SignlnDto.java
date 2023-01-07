package com.example.demo.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignlnDto {
    private String email;
    private String password;
}
