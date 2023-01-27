package com.example.demo.domain.service.Impl;

import com.example.demo.domain.Member;
import com.example.demo.domain.Role;
import com.example.demo.domain.repository.MemberRepository;
import com.example.demo.domain.service.AdminService;
import com.example.demo.global.exception.ErrorCode;
import com.example.demo.global.exception.error.MemberNotFindException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantRole(Long memberIdx){
        Member member = memberRepository.findById(memberIdx)
                .orElseThrow(()->new MemberNotFindException("Can't find member by email", ErrorCode.MEMBER_NOT_FIND));
        member.getRoles().add(Role.ROLE_MEMBER);
    }
}
