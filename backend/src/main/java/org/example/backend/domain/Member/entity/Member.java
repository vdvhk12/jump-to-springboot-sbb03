package org.example.backend.domain.Member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.backend.domain.Member.form.MemberForm;
import org.example.backend.domain.Member.model.MemberRole;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String nickname;

    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(unique = true, length = 200)
    private String refreshToken;

    public static Member of(MemberForm memberForm) {
        return Member.builder()
            .username(memberForm.getUsername())
            .nickname(memberForm.getNickname())
            .password(memberForm.getPassword())
            .email(memberForm.getEmail())
            .role(MemberRole.USER)
            .build();
    }
}
