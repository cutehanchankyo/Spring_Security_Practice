package com.example.demo.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aspectj.apache.bcel.classfile.Unknown;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private boolean success;
    private String msg;
    private int status;
}
