package com.sparklyminds.quickcheck.assessment.mapper;

import com.sparklyminds.quickcheck.assessment.dto.AssessmentRequest;
import com.sparklyminds.quickcheck.assessment.dto.AssessmentResponse;
import com.sparklyminds.quickcheck.assessment.entity.Assessment;
import com.sparklyminds.quickcheck.assessment.entity.AssessmentTranslation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssessmentMapper {

    public Assessment toEntity(AssessmentRequest request) {
        Assessment assessment = new Assessment();
        assessment.setCode(request.getCode());
        assessment.setEnabled(request.getEnabled());
        return assessment;
    }

    public AssessmentResponse toResponse(Assessment assessment) {
        return AssessmentResponse.builder()
                .id(assessment.getId())
                .code(assessment.getCode())
                .enabled(assessment.isEnabled())
                .translations(
                        assessment.getTranslations()
                                .stream()
                                .map(this::toTranslationResponse)
                                .toList()
                )
                .build();
    }

    public void updateEntity(Assessment assessment, AssessmentRequest request) {
        assessment.setCode(request.getCode());
        assessment.setEnabled(request.getEnabled());
    }

    // Translations

    public AssessmentTranslation toTranslationEntity(AssessmentRequest.Translation request) {
        AssessmentTranslation translation = new AssessmentTranslation();
        translation.setLanguage(request.getLanguage());
        translation.setName(request.getName());
        translation.setDescription(request.getDescription());
        return translation;
    }

    public void updateTranslationEntity(AssessmentTranslation translation, AssessmentRequest.Translation request) {
        if (request.getLanguage() != null) translation.setLanguage(request.getLanguage());
        if (request.getName() != null) translation.setName(request.getName());
        if (request.getDescription() != null) translation.setDescription(request.getDescription());
    }

    private AssessmentResponse.Translation toTranslationResponse(AssessmentTranslation translation) {
        return AssessmentResponse.Translation.builder()
                .id(translation.getId())
                .language(translation.getLanguage())
                .name(translation.getName())
                .description(translation.getDescription())
                .build();
    }
}