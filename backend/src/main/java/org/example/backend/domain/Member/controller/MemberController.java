package org.example.backend.domain.Member.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.Member.dto.MemberDto;
import org.example.backend.domain.Member.form.MemberForm;
import org.example.backend.domain.Member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberDto> signup(@RequestBody MemberForm memberForm) {
        MemberDto signup = memberService.signup(memberForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(signup);
    }
}
