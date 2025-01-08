package org.example.backend.domain.Member.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.backend.domain.Member.entity.Member;

@Getter
@Builder
public class MemberDto {

    private Long id;
    private String username;
    private String nickname;
    private String email;

    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
            .id(member.getId())
            .username(member.getUsername())
            .nickname(member.getNickname())
            .email(member.getEmail())
            .build();
    }
}
