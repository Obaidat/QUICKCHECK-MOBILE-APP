package com.sparklyminds.quickcheck.scoring.controller;

import com.sparklyminds.quickcheck.scoring.dto.ScoringRequest;
import com.sparklyminds.quickcheck.scoring.dto.ScoringResponse;
import com.sparklyminds.quickcheck.scoring.service.ScoringService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scoring-rules")
@RequiredArgsConstructor
public class ScoringController {

    private final ScoringService scoringRuleService;

    // Get all scoring rules for a question
    @GetMapping("/question/{questionId}")
    public ResponseEntity<ScoringResponse> getByQuestion(@PathVariable Long questionId) {
        return ResponseEntity.ok(scoringRuleService.getByQuestionId(questionId));
    }

    // Get scoring rule by ID
    @GetMapping("/{id}")
    public ResponseEntity<ScoringResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(scoringRuleService.getById(id));
    }

    @GetMapping("/question/{questionId}/red-flag")
    public ResponseEntity<Boolean> isRedFlag(@PathVariable Long questionId) {
        return ResponseEntity.ok(scoringRuleService.isRedFlag(questionId));
    }

    // Create scoring rule
    @PostMapping
    public ResponseEntity<ScoringResponse> create(@Valid @RequestBody ScoringRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scoringRuleService.create(request));
    }

    // Update scoring rule
    @PutMapping("/admin/{id}")
    public ResponseEntity<ScoringResponse> update(@PathVariable Long id, @Valid @RequestBody ScoringRequest request) {
        return ResponseEntity.ok(scoringRuleService.update(id, request));
    }

    // Delete scoring rule
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scoringRuleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}