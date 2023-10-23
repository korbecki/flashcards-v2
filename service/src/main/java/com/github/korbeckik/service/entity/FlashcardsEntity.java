package com.github.korbeckik.service.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Table("flashcards")
public class FlashcardsEntity {

    @Id
    @Column("flashcard_id")
    private Long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("icon")
    private String icon;

    @Column("is_public")
    private Boolean isPublic;

    @Column("created_by")
    private Long createdBy;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Transient
    private List<PagesEntity> pages = new ArrayList<>();
}
