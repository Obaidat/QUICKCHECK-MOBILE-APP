package com.sparklyminds.quickcheck.result.entity;

import com.sparklyminds.quickcheck.assessment.entity.Assessment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "result")
@Getter
@Setter
@NoArgsConstructor
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    @Column(nullable = false)
    private int minPoints;

    @Column(nullable = false)
    private int maxPoints;

    @Column(nullable = false)
    private boolean redFlag = false;

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultTranslation> translations = new ArrayList<>();

    public void addTranslation(ResultTranslation translation) {
        translations.add(translation);
        translation.setResult(this);
    }
}