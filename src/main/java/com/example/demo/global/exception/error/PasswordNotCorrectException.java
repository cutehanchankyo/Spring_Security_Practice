package com.example.demo.global.exception.error;

import com.example.demo.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PasswordNotCorrectException extends RuntimeException{

    private ErrorCode errorCode;

    public PasswordNotCorrectException(String msg, ErrorCode errorCode){
        super(msg);
        this.errorCode=errorCode;
    }
}
