package org.example.backend.domain.question.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {

    private Long categoryId;
    private String subject;
    private String content;
}
