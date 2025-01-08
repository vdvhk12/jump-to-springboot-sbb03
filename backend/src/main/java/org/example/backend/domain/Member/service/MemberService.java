package org.example.backend.domain.Member.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.Member.dto.MemberDto;
import org.example.backend.domain.Member.entity.Member;
import org.example.backend.domain.Member.form.MemberForm;
import org.example.backend.domain.Member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDto signup(MemberForm memberForm) {
        memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));
        return MemberDto.fromEntity(memberRepository.save(Member.of(memberForm)));
    }

}
