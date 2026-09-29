package com.sparklyminds.quickcheck.result.entity;

import com.sparklyminds.quickcheck.common.enums.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "result_translations")
@Getter
@Setter
@NoArgsConstructor
public class ResultTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "result_id", nullable = false)
    private Result result;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Language language;

    @Column(nullable = false)
    private String headAnswer;

    @Column(nullable = false)
    private String shortAnswer;

    @Column(nullable = false)
    private String fullAnswer;
}
