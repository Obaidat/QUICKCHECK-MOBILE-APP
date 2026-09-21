package com.sparklyminds.quickcheck.assessment.dto;

import com.sparklyminds.quickcheck.common.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssessmentTranslationRequest {

    @NotNull
    private Language language;

    @NotBlank
    @Size(max = 255)
    private String name;

    private String description;
}