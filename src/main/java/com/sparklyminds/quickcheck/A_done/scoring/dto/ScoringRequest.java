package com.sparklyminds.quickcheck.A_done.scoring.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoringRequest {

    @NotNull
    private Long questionId;

    @NotNull
    private Boolean answerValue;

    @NotNull
    private Integer points;

    @NotNull
    private Boolean redFlag;
}