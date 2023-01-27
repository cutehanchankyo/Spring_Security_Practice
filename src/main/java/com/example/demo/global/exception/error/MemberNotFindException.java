package com.example.demo.global.exception.error;

import com.example.demo.domain.repository.MemberRepository;
import com.example.demo.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class MemberNotFindException extends RuntimeException{

    private ErrorCode errorCode;

    public MemberNotFindException(String msg, ErrorCode errorCode){
        super(msg);
        this.errorCode=errorCode;
    }
}

