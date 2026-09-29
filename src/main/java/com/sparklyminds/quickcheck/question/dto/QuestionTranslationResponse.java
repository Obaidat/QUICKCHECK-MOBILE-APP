package com.sparklyminds.quickcheck.question.dto;

import com.sparklyminds.quickcheck.common.enums.Language;
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