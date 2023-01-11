package com.example.demo.domain.service;
import com.example.demo.domain.dto.request.MemberReqDto;
import com.example.demo.domain.dto.request.SignInDto;
import com.example.demo.domain.dto.response.MemberResDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public interface MemberService {
    Long join(MemberReqDto memberReqDto);

    Map<String, Objects> login(SignInDto signInDto);

    void logout();

    MemberResDto getMemberByIdx(Long memberIdx);

    List<MemberResDto> getAllMember();

    void withdrawal();

    MemberResDto findMe();
}