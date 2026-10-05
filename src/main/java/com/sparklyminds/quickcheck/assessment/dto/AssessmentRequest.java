package com.sparklyminds.quickcheck.assessment.dto;

import com.sparklyminds.quickcheck.common.enums.Language;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AssessmentRequest {

    @NotBlank
    @Size(max = 50)
    private String code;

    @NotNull
    private Boolean enabled = true;

    @NotEmpty
    @Valid
    private List<Translation> translations;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Translation {

        @NotNull
        private Language language;

        @NotBlank
        @Size(max = 255)
        private String name;

        private String description;
    }
}