package com.sparklyminds.quickcheck.A_done.assessment.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class AssessmentResponse {
    private Long id;
    private String code;
    private Boolean enabled;
    private BigDecimal price;
    private List<AssessmentTranslationResponse> translations;
}