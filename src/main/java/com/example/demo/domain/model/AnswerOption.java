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
@Table(name = "answer_option")
public class AnswerOption extends BaseEntity {

    @NotNull
    @Column(name = "label")
    private Character label;

    @NotNull
    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "correct_option")
    private Boolean isCorrectOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_option_question"))
    private Question question;
}
