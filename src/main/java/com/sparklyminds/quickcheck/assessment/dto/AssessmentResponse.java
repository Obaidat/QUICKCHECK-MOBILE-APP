package com.sparklyminds.quickcheck.assessment.dto;

import com.sparklyminds.quickcheck.common.enums.Language;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class AssessmentResponse {
    private Long id;
    private String code;
    private Boolean enabled;
    private List<Translation> translations;

    @Getter
    @Builder
    public static class Translation {
        private Long id;
        private Language language;
        private String name;
        private String description;
    }
}