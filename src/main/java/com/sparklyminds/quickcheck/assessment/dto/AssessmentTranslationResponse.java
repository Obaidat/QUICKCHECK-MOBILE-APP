package com.sparklyminds.quickcheck.assessment.dto;

import com.sparklyminds.quickcheck.common.enums.Language;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AssessmentTranslationResponse {
    private Long id;
    private Language language;
    private String name;
    private String description;
}