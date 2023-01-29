package com.example.demo.domain.service.Impl;

import com.example.demo.domain.Member;
import com.example.demo.domain.presentation.dto.request.MemberRequest;
import com.example.demo.domain.presentation.dto.request.SignIn;
import com.example.demo.domain.presentation.dto.response.MemberResponse;
import com.example.demo.domain.repository.MemberRepository;
import com.example.demo.domain.service.MemberService;
import com.example.demo.global.configuration.security.jwt.TokenProvider;
import com.example.demo.global.exception.ErrorCode;
import com.example.demo.global.exception.error.DuplicateMemberException;
import com.example.demo.global.exception.error.MemberNotFindException;
import com.example.demo.global.exception.error.PasswordNotCorrectException;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long join(MemberRequest memberDto){
        if(!memberRepository.findByEmail(memberDto.getEmail()).isEmpty()){
            throw new DuplicateMemberException("Member already exists", ErrorCode.DUPLICATE_MEMBER);
        }
        Member member = memberDto.toEntity(passwordEncoder.encode(memberDto.getPassword()));
        return memberRepository.save(member).getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map <String, Object> login(SignIn signIn){
        Optional<Member> byEmail = memberRepository.findByEmail(signIn.getEmail());
        if(byEmail.isEmpty()){
            throw new MemberNotFindException("Can't find member by email", ErrorCode.MEMBER_NOT_FIND);
        }
        Member member = byEmail.get();
        if(!passwordEncoder.matches(signIn.getPassword(), member.getPassword())){
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
    public MemberResponse getMemberByIdx(Long memberIdx) {
        Member member = memberRepository.findById(memberIdx)
                .orElseThrow(() -> new MemberNotFindException("Can't find member by email", ErrorCode.MEMBER_NOT_FIND));
        return ResponseDtoUtil.mapping(member, MemberResponse.class);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<MemberResponse> getAllMember() {
        List<Member> all = memberRepository.findAll();
        return ResponseDtoUtil.mapAll(all, MemberResponse.class);
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
    public MemberResponse findMe() {
        Member member = currentMemberUtil.getCurrentMember();
        return ResponseDtoUtil.mapping(member, MemberResponse.class);
    }


    private Map<String, Object> getLoginResponse(Member member, String accessToken, String refreshToken) {
        Map<String, Object> login = new HashMap<>();
        login.put("member_id", member.getId());
        login.put("accessToken", accessToken);
        login.put("refreshToken", refreshToken);
        return login;
    }
}
