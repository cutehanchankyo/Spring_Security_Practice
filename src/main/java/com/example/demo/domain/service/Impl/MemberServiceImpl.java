package com.example.demo.domain.service.Impl;

import com.example.demo.domain.Member;
import com.example.demo.domain.dto.request.MemberReqDto;
import com.example.demo.domain.dto.request.SignInDto;
import com.example.demo.domain.dto.response.MemberResDto;
import com.example.demo.domain.repository.MemberRepository;
import com.example.demo.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.DuplicateFormatFlagsException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CurrentMemberUtil currentMemberUtil;

    private final MemberReqDto memberReqDto;
    private final MemberResDto memberResDto;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long join(MemberReqDto memberReqDto){
        if(!memberRepository.findByEmail(memberReqDto.getEmail()).isEmpty()){
            throw new DuplicateFormatFlagsException("Memeber already exists", ErrorCode.DUPLICATE_MEMBER);
        }
        Member member = memberReqDto.toEntity(passwordEncoder.encode(memberReqDto.getPassword()));
        return memberRepository.save(member).getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Objects> login(SignInDto signInDto){
        Optional<Member> byEmail  = memberRepository.findByEmail(signInDto.getEmail());
        if(byEmail.isEmpty()){
            throw new MemberNotFindException("Can't find member by email",ErrorCode.MEMBER_NOT_FIND);
        }
        Member member = byEmail.get();
        if(!passwordEncoder.matches(signInDto.getPassword(), member.getPassword())){
            throw new PasswordNotFindException("Password Not Matches", ErrorCode.PASSWORD_NOT_CORRECT);
        }
        String accessToken = tokenProvider.generateAccessToken(member.getEmail());
        String refreshToken = tokenProvider.generateRefreshToken(member.getEmail());
        member.updateRefrachToken(refreshToken);
        Map<String, Objects> loginResponse = getLoginResponse(member, accessToken, refreshToken);
        return loginResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logout(){
        Member member = currentMemberUtil.getCurrentMember();
        member.updateRefrachToken(null);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public MemberResDto getMemberByIdx(Long memberIdx){
        Member member = memberRepository.findById(memberIdx)
                .orElseThrow(() -> new MemberNotFindException("Can't find member by email", ErrorCode.MEMBER_NOT_FIND));
        return ResponseDtoUtil.mapping(member, MemberResDto.class);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<MemberResDto> getAllMember(){
        List<Member> all = memberRepository.findAll();
        return ResponseDtoUtil.mapAll(all,MemberResDto.class)
    }
}
