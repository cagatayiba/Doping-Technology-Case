package com.example.demo.domain;

import com.example.demo.domain.base.BaseEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @Column(name = "label")
    private String label;

    @Column(name = "content")
    private String content;

    @Column(name = "correct_option")
    private Boolean correctOption;

    @ManyToOne
    private Question question;
}
