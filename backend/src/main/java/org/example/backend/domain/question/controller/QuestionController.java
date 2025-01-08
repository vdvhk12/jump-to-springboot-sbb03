package org.example.backend.domain.question.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.domain.question.dto.QuestionDetailDto;
import org.example.backend.domain.question.dto.QuestionListDto;
import org.example.backend.domain.question.form.QuestionForm;
import org.example.backend.domain.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/create")
    public ResponseEntity<QuestionDetailDto> createQuestion(@RequestBody QuestionForm questionForm) {
        QuestionDetailDto questionDetailDto = questionService.createQuestion(questionForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(questionDetailDto);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<QuestionListDto>> getQuestions(
        @RequestParam(value = "page", defaultValue = "1") int page) {
        Page<QuestionListDto> questions = questionService.getAllQuestions(page);
        return ResponseEntity.status(HttpStatus.OK).body(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDetailDto> getQuestion(@PathVariable Long id,
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "sort", defaultValue = "") String sort) {

        QuestionDetailDto questionDetailDto = questionService.getQuestion(id, page, sort);
        return ResponseEntity.status(HttpStatus.OK).body(questionDetailDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<QuestionDetailDto> updateQuestion(@PathVariable("id") Long id, @RequestBody QuestionForm questionForm) {
        QuestionDetailDto questionDetailDto = questionService.updateQuestion(id, questionForm);
        return ResponseEntity.status(HttpStatus.OK).body(questionDetailDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
