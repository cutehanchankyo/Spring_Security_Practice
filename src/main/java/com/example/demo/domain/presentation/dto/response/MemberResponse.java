package com.example.demo.domain.presentation.dto.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {
    private Long id;
    private String name;
}
