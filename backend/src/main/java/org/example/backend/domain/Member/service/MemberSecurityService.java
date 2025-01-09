package org.example.backend.domain.Member.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.backend.domain.Member.entity.Member;
import org.example.backend.domain.Member.model.CustomUserDetails;
import org.example.backend.domain.Member.model.MemberRole;
import org.example.backend.domain.Member.repository.MemberRepository;
import org.example.backend.global.exception.DataNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
            .orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));

        return new CustomUserDetails(member.getId(), member.getUsername(), member.getNickname(),
            member.getPassword(), new ArrayList<>());
    }

    public boolean verifyPassword(String userPassword, String reqPassword) {
        return passwordEncoder.matches(reqPassword, userPassword);
    }

    public void saveRefreshToken(String username, String refreshToken) {
        Member member = memberRepository.findByUsername(username)
            .orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));

        memberRepository.save(member.toBuilder()
            .refreshToken(refreshToken)
            .build());
    }
}
