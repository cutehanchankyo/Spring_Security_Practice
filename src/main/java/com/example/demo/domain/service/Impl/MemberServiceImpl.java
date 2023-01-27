package com.example.demo.domain.service.Impl;

import com.example.demo.domain.Member;
import com.example.demo.domain.dto.request.MemberReqDto;
import com.example.demo.domain.dto.request.SignInDto;
import com.example.demo.domain.dto.response.MemberResDto;
import com.example.demo.domain.repository.MemberRepository;
import com.example.demo.domain.service.MemberService;
import com.example.demo.global.configuration.security.jwt.TokenProvider;
import com.example.demo.global.exception.ErrorCode;
import com.example.demo.global.util.CurrentMemberUtil;
import com.example.demo.global.util.ResponseDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.List;
import java.util.*;

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
    public Long join(MemberReqDto memberDto){
        if(!memberRepository.findByEmail(memberDto.getEmail()).isEmpty()){
            throw new DuplicateMemberException("Member already exists", ErrorCode.DUPLICATE_MEMBER);
        }
        Member member = memberDto.toEntity(passwordEncoder.encode(memberDto.getPassword()));
        return memberRepository.save(member).getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> login(SignInDto signInDto){
        Optional<Member> byEmail = memberRepository.findByEmail(signInDto.getEmail());
        if(byEmail.isEmpty()){
            throw new MemberNotFindException("Can't find member by email", ErrorCode.MEMBER_NOT_FIND);
        }
        Member member = byEmail.get();
        if(!passwordEncoder.matches(signInDto.getPassword(), member.getPassword())){
            throw new PasswordNotCorrectException("Password Not Matches", ErrorCode.PASSWORD_NOT_CORRECT);
        }
        String accessToken = tokenProvider.generateAccessToken(member.getEmail());
        String refreshToken = tokenProvider.generateRefreshToken(member.getEmail());
        member.updateRefreshToken(refreshToken);
        Map<String, Object> loginResponse = getLoginResponse(member, accessToken, refreshToken);
        return loginResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logout() {
        Member member = currentMemberUtil.getCurrentMember();
        member.updateRefreshToken(null);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public MemberResDto getMemberByIdx(Long memberIdx) {
        Member member = memberRepository.findById(memberIdx)
                .orElseThrow(() -> new MemberNotFindException("Can't find member by email", ErrorCode.MEMBER_NOT_FIND));
        return ResponseDtoUtil.mapping(member, MemberResDto.class);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<MemberResDto> getAllMember() {
        List<Member> all = memberRepository.findAll();
        return ResponseDtoUtil.mapAll(all, MemberResDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdrawal() {
        Member member = currentMemberUtil.getCurrentMember();
        logout();
        memberRepository.delete(member);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public MemberResDto findMe() {
        Member member = currentMemberUtil.getCurrentMember();
        return ResponseDtoUtil.mapping(member, MemberResDto.class);
    }


    private Map<String, Object> getLoginResponse(Member member, String accessToken, String refreshToken) {
        Map<String, Object> login = new HashMap<>();
        login.put("member_id", member.getId());
        login.put("accessToken", accessToken);
        login.put("refreshToken", refreshToken);
        return login;
    }
}
