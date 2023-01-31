package com.example.demo.domain.presentation;

import com.example.demo.domain.presentation.dto.response.MemberResponse;
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
    public SingleResultResponse<MemberResponse> getOneMember(@PathVariable Long memberIdx){
        return responseService.getSingleResult(memberService.getMemberByIdx(memberIdx));
    }

    @GetMapping("/member")
    public ListResultResponse<MemberResponse> getAllmember(){
        return responseService.getListResult(memberService.getAllMember());
    }


    @PatchMapping("/grant/member/{memberIdx}")
    public CommonResultResponse grantRole(@PathVariable Long memberIdx){
        adminService.grantRole(memberIdx);
        return responseService.getSuccessResult();
    }

}
