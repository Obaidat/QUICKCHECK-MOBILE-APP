package com.sparklyminds.quickcheck.question.controller;

import com.sparklyminds.quickcheck.question.dto.QuestionRequest;
import com.sparklyminds.quickcheck.question.dto.QuestionResponse;
import com.sparklyminds.quickcheck.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // ---------------------- PUBLIC ---------------------- //

    @GetMapping("/assessment/{assessmentId}")
    public ResponseEntity<List<QuestionResponse>> getEnabledQuestionsByAssessment(@PathVariable Long assessmentId) {
        return ResponseEntity.ok(questionService.getEnabledByAssessmentId(assessmentId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getById(id));
    }

    // ---------------------- ADMIN ---------------------- //

    @GetMapping("/admin/assessment/{assessmentId}")
    public ResponseEntity<List<QuestionResponse>> getAllQuestionsByAssessment(@PathVariable Long assessmentId) {
        return ResponseEntity.ok(questionService.getAllByAssessmentId(assessmentId));
    }

    @PostMapping("/admin")
    public ResponseEntity<QuestionResponse> createQuestion(@Valid @RequestBody QuestionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.create(request));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable Long id, @Valid @RequestBody QuestionRequest request) {
        return ResponseEntity.ok(questionService.update(id, request));
    }

    @PatchMapping("/admin/{id}/enabled")
    public ResponseEntity<QuestionResponse> updateEnabled(@PathVariable Long id, @RequestParam boolean enabled) {
        return ResponseEntity.ok(questionService.updateEnabled(id, enabled));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}