package com.example.demo.global.exception.error;

import com.example.demo.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateMemberException extends RuntimeException {

    private ErrorCode errorCode;

    public DuplicateMemberException(String msg, ErrorCode errorCode){
        super(msg);
        this.errorCode=errorCode;
    }
}
