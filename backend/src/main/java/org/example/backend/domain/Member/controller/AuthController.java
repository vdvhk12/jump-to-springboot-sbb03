package org.example.backend.domain.Member.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.Member.dto.MemberLoginDto;
import org.example.backend.domain.Member.form.LoginForm;
import org.example.backend.domain.Member.model.CustomUserDetails;
import org.example.backend.domain.Member.service.MemberSecurityService;
import org.example.backend.global.jwt.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class AuthController {

    private final MemberSecurityService memberSecurityService;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        CustomUserDetails customUserDetails = memberSecurityService.loadUserByUsername(
            loginForm.getUsername());

        if (memberSecurityService.verifyPassword(customUserDetails.getPassword(),
            loginForm.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 비밀번호 입니다.");
        }

        String accessToken = jwtProvider.createAccessToken(customUserDetails.getUsername());
        String refreshToken = jwtProvider.createRefreshToken(customUserDetails.getUsername());
        memberSecurityService.saveRefreshToken(customUserDetails.getUsername(), refreshToken);

        MemberLoginDto member = MemberLoginDto.of(customUserDetails.getId(),
            customUserDetails.getNickname(),
            customUserDetails.getUsername(),
            accessToken, refreshToken);

        return ResponseEntity.status(HttpStatus.OK).body(member);
    }
}
