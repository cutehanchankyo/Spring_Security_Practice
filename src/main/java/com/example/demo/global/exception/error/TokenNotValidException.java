package com.example.demo.global.exception.error;

import com.example.demo.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class TokenNotValidException extends RuntimeException{

    private ErrorCode errorCode;

    public TokenNotValidException(String msg, ErrorCode errorCode){
        super(msg);
        this.errorCode=errorCode;
    }

}
