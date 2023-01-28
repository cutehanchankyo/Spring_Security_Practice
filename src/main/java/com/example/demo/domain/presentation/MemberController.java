package com.example.demo.domain.presentation;

import com.example.demo.domain.presentation.dto.request.MemberRequest;
import com.example.demo.domain.presentation.dto.request.SignIn;
import com.example.demo.domain.service.MemberService;
import com.example.demo.global.util.response.ResponseService;
import com.example.demo.global.util.response.result.CommonResultResponse;
import com.example.demo.global.util.response.result.SingleResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final ResponseService responseService;

    @PostMapping("/join")
    public CommonResultResponse join(@RequestBody MemberRequest memberReqDto){
        memberService.join(memberReqDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("/login")
    public SingleResultResponse login(@RequestBody SignIn signInDto){
        return responseService.getSingleResult(memberService.login(signInDto));
    }

    @PostMapping("logout")
    public CommonResultRespnose logout(){
        memberService.logout();
        return responseService.getSuccessResult();
    }

    @DeleteMapping
    public CommonResultRespnonse withdrawal(){
        memberService.withdrawal();
        return responseService.getSuccessResult();
    }

    @GetMapping("/me")
    public SingleResultResponse findMe(){
        return responseService.getSingleResult(memberService.findMe());
    }
}
