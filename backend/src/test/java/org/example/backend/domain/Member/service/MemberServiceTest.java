package org.example.backend.domain.Member.service;

import static org.assertj.core.api.Assertions.*;
import static org.example.backend.domain.util.MemberUtils.createTestMemberForm;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.example.backend.domain.Member.dto.MemberDto;
import org.example.backend.domain.Member.entity.Member;
import org.example.backend.domain.Member.form.MemberForm;
import org.example.backend.domain.Member.repository.MemberRepository;
import org.example.backend.domain.Member.model.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("signup")
    void signup() {
        //given
        MemberForm memberForm = createTestMemberForm("user1", "user1", "user1", "user1",
            "user1@gmail.com");
        Member member = Member.of(memberForm).toBuilder()
            .id(1L)
            .build();

        when(memberRepository.save(any(Member.class))).thenReturn(member);

        //when
        MemberDto result = memberService.signup(memberForm);

        //then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUsername()).isEqualTo("user1");
        assertThat(result.getNickname()).isEqualTo("user1");
        assertThat(result.getEmail()).isEqualTo("user1@gmail.com");
        assertThat(member.getRole()).isEqualTo(MemberRole.USER);
    }
}