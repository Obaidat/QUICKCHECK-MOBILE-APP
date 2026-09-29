package com.sparklyminds.quickcheck.question.dto;

import com.sparklyminds.quickcheck.common.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionTranslationRequest {

    @NotNull
    private Language language;

    @NotBlank
    private String text;
}