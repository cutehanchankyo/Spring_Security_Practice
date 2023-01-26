package com.example.demo.global.configuration.security.auth;

import com.example.demo.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class AuthDetails implements UserDetails {
    private final Member member;
    public String getEmail(){
        return member.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return member.getRoles();
    }

    @Override
    public String getPassword(){
        return member.getPassword();
    }

    @Override
    public String getUsername(){
        return member.getName();
    }

    @Override
    public boolean isAccountNonExpired(){
        return false;
    }

    @Override
    public boolean isAccountNonLocked(){
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return false;
    }

    @Override
    public boolean isEnabled(){
        return false;
    }
}
