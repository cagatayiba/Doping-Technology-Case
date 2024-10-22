package com.example.demo.domain.model;

import com.example.demo.domain.model.base.BaseEntity;
import com.example.demo.exception.IllegalArgumentException;
import com.example.demo.exception.InternalServerException;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.demo.exception.message.ErrorMessage.QUESTION_CANNOT_BE_NULL_TO_ADD_TEST;

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
    List<Question> questions = new ArrayList<>();

    public void addQuestion(Question question) {
        if (questions == null) throw new IllegalArgumentException(QUESTION_CANNOT_BE_NULL_TO_ADD_TEST);
        if(question.getNumber() == null){
            var currentQuestionCount = getQuestions().size();
            question.setNumber(currentQuestionCount+1);
        }
        getQuestions().add(question);
        question.setTest(this);
    }

    public void sortQuestionsByNumber(){
        getQuestions().sort(Comparator.comparing(Question::getNumber));
    }
}
