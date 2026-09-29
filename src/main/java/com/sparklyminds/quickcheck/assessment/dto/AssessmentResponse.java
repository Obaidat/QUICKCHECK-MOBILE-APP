package com.sparklyminds.quickcheck.assessment.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class AssessmentResponse {
    private Long id;
    private String code;
    private Boolean enabled;
    private List<AssessmentTranslationResponse> translations;
}