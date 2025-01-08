package org.example.backend.domain.util;

import org.example.backend.domain.Member.form.MemberForm;

public class MemberUtils {

    public static MemberForm createTestMemberForm(String username, String nickname,
        String password, String verifyPassword, String email) {
        MemberForm memberForm = new MemberForm();
        memberForm.setUsername(username);
        memberForm.setNickname(nickname);
        memberForm.setPassword(password);
        memberForm.setVerifyPassword(verifyPassword);
        memberForm.setEmail(email);
        return memberForm;
    }
}
