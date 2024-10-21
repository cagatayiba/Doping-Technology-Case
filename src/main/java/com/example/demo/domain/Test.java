package com.example.demo.domain;

import com.example.demo.domain.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test")
public class Test extends BaseEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

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
