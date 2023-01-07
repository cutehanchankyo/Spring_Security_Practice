package com.example.demo.domain.dto.request;

import com.example.demo.domain.Member;
import com.example.demo.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Collection;

@Getter
@AllArgsConstructor
public class MemerReqDto {
    private String email;
    private String password;
    private String name;

    public Member toEntity(String password){
        return Member.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .roles(Collection.singletonList(Role.ROLE_MEMBER))
                .build();
    }


}
