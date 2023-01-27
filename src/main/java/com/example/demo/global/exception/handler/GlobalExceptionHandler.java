package com.example.demo.global.exception.handler;

import com.example.demo.global.exception.ErrorResponse;
import com.example.demo.global.exception.error.DuplicateMemberException;
import com.example.demo.global.exception.error.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<ErrorResponse> DuplicateMemberException(HttpServletRequest request, DuplicateMemberException ex){
        printError(request, ex, ex.getErrorCode().getMsg());
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode().isSuccess(),ex.getErrorCode().getMsg(),ex.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> TokenExpiredExceptionHandler(HttpServletRequest request, TokenExpiredException ex){
        printError(request, ex, ex.getErrorCode().getMsg());
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode().isSuccess(), ex.getErrorCode().getMsg(), ex.getErrorCode().getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }


}

