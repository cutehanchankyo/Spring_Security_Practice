package com.example.demo.domain.contoller;

import com.example.demo.domain.dto.response.MemberResDto;
import com.example.demo.domain.service.AdminService;
import com.example.demo.domain.service.MemberService;
import com.example.demo.global.util.response.ResponseService;
import com.example.demo.global.util.response.result.CommonResultResponse;
import com.example.demo.global.util.response.result.ListResultResponse;
import com.example.demo.global.util.response.result.SingleResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final AdminService adminService;
    private final ResponseService responseService;

    @GetMapping("/member/{memberIdx}")
    public SingleResultResponse<MemberResDto> getOneMember(@PathVariable Long memberIdx){
        return responseService.getSingleResult(memberService.getMemberByIdx(memberIdx));
    }

    @GetMapping("/member")
    public ListResultResponse<MemberResDto> getAllmember(){
        return responseService.getListResult(memberService.getAllMember());
    }

    @PatchMapping("/grant/member/{memberIdx}")
    public CommonResultResponse grantRole(@PathVariable Long memberIdx){
        adminService.grantRole(memberIdx);
        return responseService.getSuccessResult();
    }

}