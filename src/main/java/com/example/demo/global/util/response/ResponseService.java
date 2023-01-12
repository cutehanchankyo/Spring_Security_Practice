package com.example.demo.global.util.response;

import com.example.demo.global.util.response.result.CommonResultResponse;
import com.example.demo.global.util.response.result.ListResultResponse;
import com.example.demo.global.util.response.result.SingleResultResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ResponseService {
    @Getter
    @AllArgsConstructor
    enum response{
        SUCCESS(true, "성공하셨습니다",200);
        private boolean success;
        private String msg;
        private int status;
    }

    public CommonResultResponse getSuccessResult(){
        return getSuccessResult();
    }

    public <T>SingleResultResponse<T> getSingleResult(T data){
        return new SingleResultResponse<>(getCommonResultResponse(), data);
    }

    public <T>ListResultResponse<T> getListResult(List<T> list){
        return new ListResultResponse<>(getCommonResultResponse(), list);
    }

    private CommonResultResponse getCommonResultResponse() {
        return new CommonResultResponse(response.SUCCESS.isSuccess(), response.SUCCESS.getMsg(), response.SUCCESS.getStatus());
    }
}
