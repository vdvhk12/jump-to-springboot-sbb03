package org.example.backend.domain.Member.controller;

import static org.example.backend.domain.util.MemberUtils.createTestMemberForm;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.security.SecurityConfig;
import org.example.backend.domain.Member.dto.MemberDto;
import org.example.backend.domain.Member.entity.Member;
import org.example.backend.domain.Member.form.MemberForm;
import org.example.backend.domain.Member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(MemberController.class)
@Import(SecurityConfig.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/v1/members/signup")
    @WithMockUser
    void t1() throws Exception {
        //given
        String url = "/api/v1/members/signup";
        MemberForm memberForm = createTestMemberForm("user1", "user1", "user1", "user1",
            "user1@gmail.com");
        MemberDto member = MemberDto.fromEntity(Member.of(memberForm).toBuilder()
            .id(1L)
            .build());
        when(memberService.signup(any(MemberForm.class))).thenReturn(member);

        //when
        ResultActions resultActions = mockMvc.perform(post(url)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberForm)));

        //then
        resultActions.andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(member.getId()))
            .andExpect(jsonPath("$.username").value(member.getUsername()))
            .andExpect(jsonPath("$.nickname").value(member.getNickname()))
            .andExpect(jsonPath("$.email").value(member.getEmail()));

    }
}