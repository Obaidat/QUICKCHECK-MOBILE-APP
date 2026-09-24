package com.sparklyminds.quickcheck.A_done.question.dto;

import jakarta.validation.Valid;
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

    @NotNull
    private Boolean required = true;

    @NotEmpty @Valid
    private List<QuestionTranslationRequest> translations;
}
