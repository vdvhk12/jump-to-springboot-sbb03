package org.example.backend.domain.Member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberLoginDto {

    private Long id;
    private String username;
    private String nickname;
    private String accessToken;
    private String refreshToken;

    public static MemberLoginDto of(Long id, String username, String nickname, String accessToken, String refreshToken) {
        return MemberLoginDto.builder()
            .id(id)
            .username(username)
            .nickname(nickname)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
}
