package com.sparklyminds.quickcheck.scoring.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScoringResponse {

    private Long id;
    private Long questionId;
    private int points;
    private boolean redFlag;
}