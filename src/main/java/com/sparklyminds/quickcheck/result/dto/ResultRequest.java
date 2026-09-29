package com.sparklyminds.quickcheck.result.dto;

import com.sparklyminds.quickcheck.common.enums.Language;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResultRequest {

    private Long assessmentId;
    private int minPoints;
    private int maxPoints;
    private boolean redFlag;
    private List<Translation> translations;

    @Getter
    @Setter
    public static class Translation {
        private Language language;
        private String headAnswer;
        private String shortAnswer;
        private String fullAnswer;
    }
}