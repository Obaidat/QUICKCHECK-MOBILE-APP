package com.sparklyminds.quickcheck.assessment.service;

import com.sparklyminds.quickcheck.assessment.dto.AssessmentRequest;
import com.sparklyminds.quickcheck.assessment.dto.AssessmentResponse;
import com.sparklyminds.quickcheck.assessment.entity.Assessment;
import com.sparklyminds.quickcheck.assessment.entity.AssessmentTranslation;
import com.sparklyminds.quickcheck.assessment.mapper.AssessmentMapper;
import com.sparklyminds.quickcheck.assessment.repository.AssessmentRepository;
import com.sparklyminds.quickcheck.common.enums.Language;
import lombok.RequiredArgsConstructor;
import com.sparklyminds.quickcheck.common.exception.BadRequestException;
import com.sparklyminds.quickcheck.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Transactional
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final AssessmentMapper assessmentMapper;

    // ---------------------- PUBLIC ---------------------- //

    @Transactional(readOnly = true)
    public List<AssessmentResponse> getAllEnabled() {
        return assessmentRepository.findByEnabledTrueOrderByIdAsc()
                .stream()
                .map(assessmentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AssessmentResponse getById(Long id) {
        Assessment assessment = findById(id);
        if (!assessment.isEnabled()) {
            throw new ResourceNotFoundException("Assessment not found with id: " + id);
        }
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
        validateTranslations(request);
        if (assessmentRepository.existsByCode(request.getCode())) {
            throw new BadRequestException("Assessment with code already exists: " + request.getCode());
        }

        // Create assessment
        Assessment assessment = assessmentMapper.toEntity(request);

        // Add translations
        for (var translationRequest : request.getTranslations()) {
            AssessmentTranslation translation = assessmentMapper.toTranslationEntity(translationRequest);
            assessment.addTranslation(translation);
        }

        // Save assessment and translations together
        Assessment saved = assessmentRepository.save(assessment);
        return assessmentMapper.toResponse(saved);
    }

    public AssessmentResponse update(Long id, AssessmentRequest request) {
        validateTranslations(request);
        Assessment assessment = findById(id);

        if (assessment.getCode().equals(request.getCode())) {
            assessmentMapper.updateEntity(assessment, request);

            for (var translationRequest : request.getTranslations()) {
                boolean found = false;
                for (var translationEntity : assessment.getTranslations()) {
                    if (translationRequest.getLanguage().equals(translationEntity.getLanguage())) {
                        assessmentMapper.updateTranslationEntity(translationEntity, translationRequest);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    assessment.addTranslation(assessmentMapper.toTranslationEntity(translationRequest));
                }
            }
            return assessmentMapper.toResponse(assessment);
        }
        else {
            throw new BadRequestException("Assessment code cannot be changed");
        }
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

    public void deleteTranslation(Long id, Language language) {

        Assessment assessment = findById(id);

        if (assessment.getTranslations().size() <= 1) {
            throw new BadRequestException(
                    "Assessment must have at least one translation"
            );
        }

        AssessmentTranslation translation = assessment.getTranslations()
                .stream()
                .filter(t -> t.getLanguage().equals(language))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Translation not found for language: " + language
                ));
        assessment.getTranslations().remove(translation);
    }

    // ---------------------- HELPER ---------------------- //

    private Assessment findById(Long id) {
        return assessmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assessment not found with id: " + id));
    }

    private void validateTranslations(AssessmentRequest request) {
        var languages = new HashSet<Language>();
        request.getTranslations().forEach(translation -> {
            if (!languages.add(translation.getLanguage())) {
                throw new BadRequestException("An assessment can have only one translation per language");
            }
        });
    }
}
