package org.example.backend.domain.question.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionCreateForm {

    private String subject;
    private String content;
}