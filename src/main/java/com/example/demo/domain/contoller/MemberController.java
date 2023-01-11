package com.example.demo.domain.contoller;

import com.example.demo.domain.dto.request.MemberReqDto;
import com.example.demo.domain.dto.request.SignInDto;
import com.example.demo.domain.service.MemberService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final ResponseService responseService;

    @PostMapping("/join")
    public CommonResultResponse join(@RequestBody MemberReqDto memberReqDto){
        memberService.join(memberReqDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("/login")
    public SingleResultResponse login(@RequestBody SignInDto signInDto){
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
