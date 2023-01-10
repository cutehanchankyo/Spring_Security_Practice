package com.example.demo.domain.service.Impl;

import com.example.demo.domain.Member;
import com.example.demo.domain.dto.request.MemberReqDto;
import com.example.demo.domain.repository.MemberRepository;
import com.example.demo.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.DuplicateFormatFlagsException;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CurrentMemberUtil currentMemberUtil;

    private final MemberReqDto memberReqDto;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long join(MemberReqDto memberReqDto){
        if(!memberRepository.findOneByEmail(memberReqDto.getEmail()).isEmpty()){
            throw new DuplicateFormatFlagsException("Memeber already exists", ErrorCode.DUPLICATE_MEMBER);
        }
        Member member = memberReqDto.toEntity(passwordEncoder.encode(memberReqDto.getPassword()));
        return memberRepository.save(member).getId();
    }



}
