package com.example.demo.domain.service;

import com.example.demo.domain.dto.request.SignInDto;
import com.example.demo.domain.dto.response.MemberResDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MemberService {
    Long join(MemberResDto memberResDto);

    Map<String, Object> login(SignInDto signInDto);

    void logout();

    MemberResDto getMemberByIdx(Long memberIdx);

    List<MemberResDto> getAllMember();

    void withdrawal();

    MemberResDto findMe();
}
