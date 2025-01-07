package org.example.backend.domain.answer.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {

    private Long questionId;
    private String content;
}
