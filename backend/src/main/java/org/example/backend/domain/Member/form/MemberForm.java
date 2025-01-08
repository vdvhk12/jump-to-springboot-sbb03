package org.example.backend.domain.Member.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    private String username;
    private String nickname;
    private String password;
    private String verifyPassword;
    private String email;
}
