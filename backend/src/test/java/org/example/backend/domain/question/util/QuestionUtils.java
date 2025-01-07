package org.example.backend.domain.question.util;

import java.time.LocalDateTime;
import org.example.backend.domain.question.entity.Question;
import org.example.backend.domain.question.form.QuestionForm;

public class QuestionUtils {

    public static QuestionForm createTestQuestionForm(String subject, String content) {
        QuestionForm form = new QuestionForm();
        form.setSubject(subject);
        form.setContent(content);
        return form;
    }

    public static Question createTestQuestion(Long questionId, QuestionForm questionForm) {
        return Question.builder()
            .id(questionId)
            .subject(questionForm.getSubject())
            .content(questionForm.getContent())
            .createdAt(LocalDateTime.now())
            .views(0)
            .build();
    }
}
