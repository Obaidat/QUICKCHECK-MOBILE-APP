package com.sparklyminds.quickcheck.question.entity;

import com.sparklyminds.quickcheck.common.enums.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "question_translations",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_question_language",
                        columnNames = {"question_id", "language"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class QuestionTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Language language;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;
}