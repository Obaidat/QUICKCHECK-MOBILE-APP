package com.sparklyminds.quickcheck.A_done.question.entity;

import com.sparklyminds.quickcheck.A_done.assessment.entity.Assessment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    @Column(name = "question_order", nullable = false)
    private Integer questionOrder;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private boolean required = true;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionTranslation> translations = new ArrayList<>();

    public void addTranslation(QuestionTranslation translation) {
        translations.add(translation);
        translation.setQuestion(this);
    }
}
