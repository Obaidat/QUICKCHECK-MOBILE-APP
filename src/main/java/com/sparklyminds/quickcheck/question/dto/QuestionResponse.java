package com.sparklyminds.quickcheck.question.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuestionResponse {

    private Long id;
    private Long assessmentId;
    private Integer questionOrder;
    private boolean enabled;
    private boolean required;
    private List<QuestionTranslationResponse> translations;
}
