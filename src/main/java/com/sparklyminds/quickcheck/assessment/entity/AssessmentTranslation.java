package com.sparklyminds.quickcheck.assessment.entity;

import com.sparklyminds.quickcheck.common.enums.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "assessment_translations",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_assessment_language",
                        columnNames = {"assessment_id", "language"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class AssessmentTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Language language;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}