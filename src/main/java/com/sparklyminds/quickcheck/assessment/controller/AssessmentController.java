package com.sparklyminds.quickcheck.assessment.controller;

import com.sparklyminds.quickcheck.assessment.dto.AssessmentRequest;
import com.sparklyminds.quickcheck.assessment.dto.AssessmentResponse;
import com.sparklyminds.quickcheck.assessment.service.AssessmentService;
import com.sparklyminds.quickcheck.common.enums.Language;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    // ---------------------- PUBLIC ---------------------- //

    // Get all *enabled* assessments
    @GetMapping
    public ResponseEntity<List<AssessmentResponse>> getAllEnabledAssessments() {
        return ResponseEntity.ok(assessmentService.getAllEnabled());
    }

    // Get one assessment by ID
    @GetMapping("/{id}")
    public ResponseEntity<AssessmentResponse> getAssessmentById(@PathVariable Long id) {
        return ResponseEntity.ok(assessmentService.getById(id));
    }

    // ---------------------- ADMIN ---------------------- //

    // Get all assessments
    @GetMapping("/admin")
    public ResponseEntity<List<AssessmentResponse>> getAllAssessment() {
        return ResponseEntity.ok(assessmentService.getAll());
    }

    // Create a new assessment
    @PostMapping("/admin")
    public ResponseEntity<AssessmentResponse> createAssessment(@Valid @RequestBody AssessmentRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assessmentService.create(request));
    }

    // Update an assessment
    @PutMapping("/admin/{id}")
    public ResponseEntity<AssessmentResponse> updateAssessment(@PathVariable Long id, @Valid @RequestBody AssessmentRequest request) {
        return ResponseEntity.ok(assessmentService.update(id, request));
    }

    // Enable/Disable an assessment
    @PatchMapping("/admin/{id}/enabled")
    public ResponseEntity<AssessmentResponse> updateEnabled(@PathVariable Long id, @RequestParam boolean enabled) {
        return ResponseEntity.ok(assessmentService.updateEnabled(id, enabled));
    }

    @DeleteMapping("/admin/{id}/translations")
    public ResponseEntity<Void> deleteTranslation(@PathVariable Long id, @RequestParam Language language) {
        assessmentService.deleteTranslation(id, language);
        return ResponseEntity.noContent().build();
    }

    // Delete an assessment
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable Long id) {
        assessmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}