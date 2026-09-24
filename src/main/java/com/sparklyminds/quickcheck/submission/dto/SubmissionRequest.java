package com.sparklyminds.quickcheck.submission.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class SubmissionRequest {

    @NotNull
    private Long assessmentId;

    @NotEmpty @Valid
    private List<Answer> answers;

    @Getter @Setter
    public static class Answer {
        @NotNull private Long questionId;
        @NotNull private Boolean value;
    }
}
