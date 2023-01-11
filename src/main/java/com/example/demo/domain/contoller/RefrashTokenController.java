package com.example.demo.domain.contoller;

import com.example.demo.domain.service.TokenRefrachService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class RefrashTokenController {
    private final ResponseSeruvice responseSeruvice;
    private final TokenRefrachService tokenRefrachService;

    @PostMapping("/refrash")
    public SingleResultResponse refreshAccessToken(@RequestHeader String refreshToken){
        return responseSeruvice.getSingleResult(tokenRefrachService.refrash(refreshToken));
    }
}
