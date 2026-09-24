package com.sparklyminds.quickcheck.A_done.assessment.mapper;

import com.sparklyminds.quickcheck.A_done.assessment.dto.AssessmentRequest;
import com.sparklyminds.quickcheck.A_done.assessment.dto.AssessmentResponse;
import com.sparklyminds.quickcheck.A_done.assessment.entity.Assessment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssessmentMapper {

    private final AssessmentTranslationMapper translationMapper;

    public Assessment toEntity(AssessmentRequest request) {
        Assessment assessment = new Assessment();

        assessment.setCode(request.getCode());
        assessment.setEnabled(request.getEnabled());
        assessment.setPrice(request.getPrice());

        return assessment;
    }

    public AssessmentResponse toResponse(Assessment assessment) {
        return AssessmentResponse.builder()
                .id(assessment.getId())
                .code(assessment.getCode())
                .enabled(assessment.isEnabled())
                .price(assessment.getPrice())
                .translations(
                        assessment.getTranslations()
                                .stream()
                                .map(translationMapper::toResponse)
                                .toList()
                )
                .build();
    }

    public void updateEntity(
            Assessment assessment,
            AssessmentRequest request
    ) {
        assessment.setCode(request.getCode());
        assessment.setEnabled(request.getEnabled());
        assessment.setPrice(request.getPrice());
    }
}