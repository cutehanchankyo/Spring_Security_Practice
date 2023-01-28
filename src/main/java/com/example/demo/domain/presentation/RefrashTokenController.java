package com.example.demo.domain.presentation;

import com.example.demo.domain.service.TokenRefreshService;
import com.example.demo.global.util.response.result.SingleResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class RefrashTokenController {
    private final ResponseSeruvice responseSeruvice;
    private final TokenRefreshService tokenRefrachService;

    @PostMapping("/refrash")
    public SingleResultResponse refreshAccessToken(@RequestHeader String refreshToken){
        return responseSeruvice.getSingleResult(tokenRefrachService.refrash(refreshToken));
    }
}
