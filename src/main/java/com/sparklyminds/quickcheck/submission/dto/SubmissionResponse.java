package com.sparklyminds.quickcheck.submission.dto;

import com.sparklyminds.quickcheck.outcome.dto.OutcomeResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SubmissionResponse {

    private Long assessmentId;
    private int score;
    private boolean redFlag;
    private List<Long> redFlagQuestionIds;
    private OutcomeResponse outcome;
}