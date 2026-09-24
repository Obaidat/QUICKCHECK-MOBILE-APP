package com.sparklyminds.quickcheck.A_done.question.dto;

import com.sparklyminds.quickcheck.A_done.common.enums.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionTranslationResponse {

    private Long id;
    private Language language;
    private String text;
}