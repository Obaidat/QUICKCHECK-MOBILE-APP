package com.sparklyminds.quickcheck.assessment.mapper;

import com.sparklyminds.quickcheck.assessment.dto.AssessmentTranslationRequest;
import com.sparklyminds.quickcheck.assessment.dto.AssessmentTranslationResponse;
import com.sparklyminds.quickcheck.assessment.entity.AssessmentTranslation;
import org.springframework.stereotype.Component;

@Component
public class AssessmentTranslationMapper {

    public AssessmentTranslation toEntity(AssessmentTranslationRequest request) {
        AssessmentTranslation translation = new AssessmentTranslation();
        translation.setLanguage(request.getLanguage());
        translation.setName(request.getName());
        translation.setDescription(request.getDescription());
        return translation;
    }

    public AssessmentTranslationResponse toResponse(AssessmentTranslation translation) {
        return AssessmentTranslationResponse.builder()
                .id(translation.getId())
                .language(translation.getLanguage())
                .name(translation.getName())
                .description(translation.getDescription())
                .build();
    }

    public void updateEntity(AssessmentTranslation translation, AssessmentTranslationRequest request) {
        translation.setLanguage(request.getLanguage());
        translation.setName(request.getName());
        translation.setDescription(request.getDescription());
    }
}