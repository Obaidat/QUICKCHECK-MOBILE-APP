package com.sparklyminds.quickcheck.assessment.service;

import com.sparklyminds.quickcheck.assessment.dto.AssessmentTranslationRequest;
import com.sparklyminds.quickcheck.assessment.dto.AssessmentTranslationResponse;
import com.sparklyminds.quickcheck.assessment.entity.Assessment;
import com.sparklyminds.quickcheck.assessment.entity.AssessmentTranslation;
import com.sparklyminds.quickcheck.assessment.mapper.AssessmentTranslationMapper;
import com.sparklyminds.quickcheck.assessment.repository.AssessmentRepository;
import com.sparklyminds.quickcheck.assessment.repository.AssessmentTranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AssessmentTranslationService {

    private final AssessmentRepository assessmentRepository;
    private final AssessmentTranslationRepository translationRepository;
    private final AssessmentTranslationMapper translationMapper;

    @Transactional(readOnly = true)
    public List<AssessmentTranslationResponse> getByAssessmentId(
            Long assessmentId
    ) {
        findAssessment(assessmentId);

        return translationRepository
                .findByAssessmentId(assessmentId)
                .stream()
                .map(translationMapper::toResponse)
                .toList();
    }

    public AssessmentTranslationResponse create(
            Long assessmentId,
            AssessmentTranslationRequest request
    ) {
        Assessment assessment = findAssessment(assessmentId);

        if (translationRepository.existsByAssessmentIdAndLanguage(
                assessmentId,
                request.getLanguage()
        )) {
            throw new IllegalArgumentException(
                    "Translation for this language already exists"
            );
        }

        AssessmentTranslation translation =
                translationMapper.toEntity(request);

        translation.setAssessment(assessment);

        AssessmentTranslation saved =
                translationRepository.save(translation);

        return translationMapper.toResponse(saved);
    }

    public AssessmentTranslationResponse update(
            Long assessmentId,
            Long translationId,
            AssessmentTranslationRequest request
    ) {
        AssessmentTranslation translation =
                findTranslation(assessmentId, translationId);

        if (!translation.getLanguage().equals(request.getLanguage())
                && translationRepository.existsByAssessmentIdAndLanguage(
                assessmentId,
                request.getLanguage()
        )) {
            throw new IllegalArgumentException(
                    "Translation for this language already exists"
            );
        }

        translationMapper.updateEntity(translation, request);

        return translationMapper.toResponse(translation);
    }

    public void delete(
            Long assessmentId,
            Long translationId
    ) {
        AssessmentTranslation translation =
                findTranslation(assessmentId, translationId);

        translationRepository.delete(translation);
    }

    private Assessment findAssessment(Long assessmentId) {
        return assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Assessment not found with id: " + assessmentId
                ));
    }

    private AssessmentTranslation findTranslation(
            Long assessmentId,
            Long translationId
    ) {
        return translationRepository
                .findByIdAndAssessmentId(translationId, assessmentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Translation not found with id: " + translationId
                ));
    }
}