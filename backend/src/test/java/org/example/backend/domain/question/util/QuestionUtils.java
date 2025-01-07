package org.example.backend.domain.question.util;

import java.time.LocalDateTime;
import org.example.backend.domain.question.entity.Question;
import org.example.backend.domain.question.form.QuestionCreateForm;

public class QuestionUtils {

    public static QuestionCreateForm createTestQuestionForm(String subject, String content) {
        QuestionCreateForm form = new QuestionCreateForm();
        form.setSubject(subject);
        form.setContent(content);
        return form;
    }

    public static Question createTestQuestion(Long questionId, QuestionCreateForm questionCreateForm) {
        return Question.builder()
            .id(questionId)
            .subject(questionCreateForm.getSubject())
            .content(questionCreateForm.getContent())
            .createdAt(LocalDateTime.now())
            .views(0)
            .build();
    }
}
