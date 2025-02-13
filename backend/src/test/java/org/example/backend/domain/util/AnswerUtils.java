package org.example.backend.domain.util;

import java.time.LocalDateTime;
import org.example.backend.domain.answer.entity.Answer;
import org.example.backend.domain.answer.form.AnswerForm;
import org.example.backend.domain.question.entity.Question;

public class AnswerUtils {

    public static AnswerForm createTestAnswerForm(Long questionId, String content) {
        AnswerForm answerForm = new AnswerForm();
        answerForm.setQuestionId(questionId);
        answerForm.setContent(content);
        return answerForm;
    }

    public static Answer createTestAnswer(Question question, Long answerId, AnswerForm answerForm) {
        return Answer.builder()
            .id(answerId)
            .question(question)
            .content(answerForm.getContent())
            .createdAt(LocalDateTime.now())
            .build();
    }

    public static Answer updateTestAnswer(Answer answer, AnswerForm answerForm) {
        return answer.toBuilder()
            .content(answerForm.getContent())
            .updatedAt(LocalDateTime.now())
            .build();
    }
}
