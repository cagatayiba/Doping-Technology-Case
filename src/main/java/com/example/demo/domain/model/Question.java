package com.example.demo.domain.model;

import com.example.demo.domain.model.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "question")
public class Question extends BaseEntity {

    @NotNull
    @Column(name = "question_number")
    private Integer number;

    @NotNull
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_test"))
    private Test test;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    private List<AnswerOption> answerOptions = new ArrayList<>();

    public void addAnswerOption(AnswerOption answerOption) {
        getAnswerOptions().add(answerOption);
        answerOption.setQuestion(this);
    }

    public Question addAnswerOptions(List<AnswerOption> answerOptions) {
        answerOptions.forEach(this::addAnswerOption);
        return this;
    }


    public void sortAnswerOptions() {
        getAnswerOptions().sort(Comparator.comparing(AnswerOption::getLabel));
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
