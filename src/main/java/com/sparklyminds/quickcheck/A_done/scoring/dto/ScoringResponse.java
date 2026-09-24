package com.sparklyminds.quickcheck.A_done.scoring.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScoringResponse {

    private Long id;
    private Long questionId;
    private boolean answerValue;
    private int points;
    private boolean redFlag;
}