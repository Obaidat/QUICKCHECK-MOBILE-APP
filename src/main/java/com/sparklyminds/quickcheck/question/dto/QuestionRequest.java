package com.sparklyminds.quickcheck.question.dto;

import com.sparklyminds.quickcheck.common.enums.Language;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuestionRequest {

    @NotNull
    private Long assessmentId;

    @NotNull @Positive
    private Integer questionOrder;

    @NotNull
    private Boolean enabled = true;

    @NotEmpty @Valid
    private List<Translation > translations;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Translation {

        @NotNull
        private Language language;

        @NotBlank
        private String text;
    }
}
