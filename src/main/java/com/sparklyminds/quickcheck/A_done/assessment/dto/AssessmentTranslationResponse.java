package com.sparklyminds.quickcheck.A_done.assessment.dto;

import com.sparklyminds.quickcheck.A_done.common.enums.Language;
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