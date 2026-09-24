package com.sparklyminds.quickcheck.A_done.question.mapper;

import com.sparklyminds.quickcheck.A_done.question.dto.QuestionRequest;
import com.sparklyminds.quickcheck.A_done.question.dto.QuestionResponse;
import com.sparklyminds.quickcheck.A_done.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionMapper {

    private final QuestionTranslationMapper translationMapper;

    public Question toEntity(QuestionRequest request) {
        Question question = new Question();

        question.setQuestionOrder(request.getQuestionOrder());
        question.setEnabled(request.getEnabled());
        question.setRequired(request.getRequired());

        return question;
    }

    public QuestionResponse toResponse(Question question) {
        QuestionResponse response = new QuestionResponse();

        response.setId(question.getId());
        response.setAssessmentId(question.getAssessment().getId());
        response.setQuestionOrder(question.getQuestionOrder());
        response.setEnabled(question.isEnabled());
        response.setRequired(question.isRequired());

        response.setTranslations(
                question.getTranslations()
                        .stream()
                        .map(translationMapper::toResponse)
                        .toList()
        );

        return response;
    }

    public void updateEntity(Question question, QuestionRequest request) {
        question.setQuestionOrder(request.getQuestionOrder());
        question.setEnabled(request.getEnabled());
        question.setRequired(request.getRequired());
    }
}
