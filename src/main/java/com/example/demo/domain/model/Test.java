package com.example.demo.domain.model;

import com.example.demo.domain.model.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test")
public class Test extends BaseEntity {

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private TestState state;

    @OneToMany(mappedBy = "test", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    List<Question> questions;

    public void addQuestion(Question question) {
        getQuestions().add(question);
        question.setTest(this);
    }

    public void removeQuestion(Question question) {
        getQuestions().remove(question);
        question.setTest(null);
    }
}
