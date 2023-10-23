package com.github.korbeckik.service.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("pages")
public class PagesEntity {

    @Id
    @Column("page_id")
    private Long id;

    @Column("flashcard_id")
    private Long flashcardId;

    @Column("question")
    private String question;

    @Column("question_image")
    private String questionImage;

    @Column("answer")
    private String answer;

    @Column("answer_image")
    private String answerImage;

    @Column("created_at")
    private LocalDateTime createdAt;
}
