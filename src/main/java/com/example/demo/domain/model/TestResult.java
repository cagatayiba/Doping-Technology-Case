package com.example.demo.domain.model;

import com.example.demo.domain.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_test_result")
public class TestResult extends BaseEntity {

    @NotNull
    @Column(name = "number_of_correct_answers")
    private Integer numberOfCorrectAnswers;

    @NotNull
    @Column(name = "number_of_wrong_answers")
    private Integer numberOfWrongAnswers;

    @NotNull
    @Column(name = "number_of_unanswered_questions")
    private Integer numberOfUnansweredQuestions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_student_test_result_student"))
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_student_test_result_test"))
    private Test test;
}
