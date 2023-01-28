package com.example.demo.domain.service;
import com.example.demo.domain.presentation.dto.request.MemberRequest;
import com.example.demo.domain.presentation.dto.request.SignIn;
import com.example.demo.domain.presentation.dto.response.MemberResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public interface MemberService {
    Long join(MemberRequest memberReqDto);

    Map<String, Objects> login(SignIn signInDto);

    void logout();

    MemberResponse getMemberByIdx(Long memberIdx);

    List<MemberResponse> getAllMember();

    void withdrawal();

    MemberResponse findMe();
}
