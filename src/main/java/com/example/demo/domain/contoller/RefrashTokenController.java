package com.example.demo.domain.contoller;

import com.example.demo.domain.dto.response.MemberResDto;
import com.example.demo.domain.service.AdminService;
import com.example.demo.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class RefrashTokenController {
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

    @GetMapping("/grant/member/{memberIdx}")
    public CommonResultResponse grantRole(@PathVariable Long memberIdx){
        adminService.grantRole(memberIdx);
        return responseService.getSuccessResult();
    }
}
