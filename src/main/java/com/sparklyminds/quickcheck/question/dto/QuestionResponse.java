package com.sparklyminds.quickcheck.question.dto;

import com.sparklyminds.quickcheck.common.enums.Language;
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
    private List<Translation> translations;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Translation {
        private Long id;
        private Language language;
        private String text;
    }
}
