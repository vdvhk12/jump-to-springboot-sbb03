package org.example.backend.domain.Member.repository;

import static org.assertj.core.api.Assertions.*;
import static org.example.backend.domain.util.MemberUtils.createTestMemberForm;

import org.example.backend.domain.Member.entity.Member;
import org.example.backend.domain.Member.form.MemberForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("signup")
    void t1() {
        //given
        MemberForm memberForm = createTestMemberForm("user1", "user1", "user1", "user1",
            "user1@gmail.com");

        //when
        Member result = memberRepository.save(Member.of(memberForm));

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUsername()).isEqualTo(memberForm.getUsername());
        assertThat(result.getNickname()).isEqualTo(memberForm.getNickname());
        assertThat(result.getEmail()).isEqualTo(memberForm.getEmail());
    }
}