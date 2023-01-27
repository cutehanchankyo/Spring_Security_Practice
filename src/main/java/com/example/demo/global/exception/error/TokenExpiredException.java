package com.example.demo.global.exception.error;

import com.example.demo.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class TokenExpiredException extends RuntimeException{

    private ErrorCode errorCode;

    public TokenExpiredException(String msg,ErrorCode errorCode){
        super(msg);
        this.errorCode=errorCode;
    }
}
