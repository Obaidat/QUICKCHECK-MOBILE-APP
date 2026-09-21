package com.sparklyminds.quickcheck.assessment.controller;

import com.sparklyminds.quickcheck.assessment.dto.AssessmentTranslationRequest;
import com.sparklyminds.quickcheck.assessment.dto.AssessmentTranslationResponse;
import com.sparklyminds.quickcheck.assessment.service.AssessmentTranslationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/assessments/{assessmentId}/translations")
@RequiredArgsConstructor
public class AssessmentTranslationController {

    private final AssessmentTranslationService translationService;

    // Get translations for an assessment
    @GetMapping
    public ResponseEntity<List<AssessmentTranslationResponse>> getTranslations(@PathVariable Long assessmentId) {
        return ResponseEntity.ok(translationService.getByAssessmentId(assessmentId));
    }

    // Add a translation
    @PostMapping
    public ResponseEntity<AssessmentTranslationResponse> createTranslation(@PathVariable Long assessmentId, @Valid @RequestBody AssessmentTranslationRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(translationService.create(assessmentId, request));
    }

    // Update a translation
    @PutMapping("/{translationId}")
    public ResponseEntity<AssessmentTranslationResponse> updateTranslation(
            @PathVariable Long assessmentId,
            @PathVariable Long translationId,
            @Valid @RequestBody AssessmentTranslationRequest request
    ) {
        return ResponseEntity.ok(
                translationService.update(
                        assessmentId,
                        translationId,
                        request
                )
        );
    }

    // Delete a translation
    @DeleteMapping("/{translationId}")
    public ResponseEntity<Void> deleteTranslation(@PathVariable Long assessmentId, @PathVariable Long translationId
    ) {
        translationService.delete(assessmentId, translationId);
        return ResponseEntity.noContent().build();
    }
}