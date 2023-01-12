package com.example.demo.global.util;

import com.example.demo.domain.Member;
import com.example.demo.domain.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentMemberUtil {
    private final MemberRepository memberRepository;

    public static String getCurrentMail(){
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            email = ((AuthDetails) principal).getEmail();
        }else{
            email = principal.toString();
        }
        return email;
    }
}
