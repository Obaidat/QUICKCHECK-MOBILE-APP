package com.sparklyminds.quickcheck.assessment.service;

import com.sparklyminds.quickcheck.assessment.dto.AssessmentRequest;
import com.sparklyminds.quickcheck.assessment.dto.AssessmentResponse;
import com.sparklyminds.quickcheck.assessment.entity.Assessment;
import com.sparklyminds.quickcheck.assessment.entity.AssessmentTranslation;
import com.sparklyminds.quickcheck.assessment.mapper.AssessmentMapper;
import com.sparklyminds.quickcheck.assessment.mapper.AssessmentTranslationMapper;
import com.sparklyminds.quickcheck.assessment.repository.AssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final AssessmentMapper assessmentMapper;
    private final AssessmentTranslationMapper translationMapper;

    // ---------------------- PUBLIC ---------------------- //

    @Transactional(readOnly = true)
    public List<AssessmentResponse> getAllEnabled() {
        return assessmentRepository.findByEnabledTrue()
                .stream()
                .map(assessmentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AssessmentResponse getById(Long id) {
        Assessment assessment = findById(id);
        return assessmentMapper.toResponse(assessment);
    }

    // ---------------------- ADMIN ---------------------- //

    @Transactional(readOnly = true)
    public List<AssessmentResponse> getAll() {
        return assessmentRepository.findAll()
                .stream()
                .map(assessmentMapper::toResponse)
                .toList();
    }

    public AssessmentResponse create(AssessmentRequest request) {

        if (assessmentRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException(
                    "Assessment with code already exists: " + request.getCode()
            );
        }

        // Create assessment
        Assessment assessment = assessmentMapper.toEntity(request);

        // Add translations
        for (var translationRequest : request.getTranslations()) {
            AssessmentTranslation translation = translationMapper.toEntity(translationRequest);
            assessment.addTranslation(translation);
        }

        // Save assessment and translations together
        Assessment saved = assessmentRepository.save(assessment);
        return assessmentMapper.toResponse(saved);
    }

    public AssessmentResponse update(Long id, AssessmentRequest request) {
        Assessment assessment = findById(id);
        if (!assessment.getCode().equals(request.getCode())
                && assessmentRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException(
                    "Assessment with code already exists: " + request.getCode()
            );
        }

        // Update assessment fields
        assessmentMapper.updateEntity(assessment, request);

        // Replace translations
        assessment.getTranslations().clear();
        for (var translationRequest : request.getTranslations()) {
            AssessmentTranslation translation = translationMapper.toEntity(translationRequest);
            assessment.addTranslation(translation);
        }
        return assessmentMapper.toResponse(assessment);
    }

    public AssessmentResponse updateEnabled(Long id, boolean enabled) {
        Assessment assessment = findById(id);
        assessment.setEnabled(enabled);
        return assessmentMapper.toResponse(assessment);
    }

    public void delete(Long id) {
        Assessment assessment = findById(id);
        assessmentRepository.delete(assessment);
    }

    // ---------------------- HELPER ---------------------- //

    private Assessment findById(Long id) {
        return assessmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Assessment not found with id: " + id
                ));
    }
}