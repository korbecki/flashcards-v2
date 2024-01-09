package com.github.korbeckik.service.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("resolved")
public class ResolvedEntity {
    @Id
    @Column("resolved_id")
    private Long id;

    @Column("user_id")
    private Long userId;

    @Column("page_id")
    private Long pageId;

    @Column("answer")
    private String answer;

    @Column("is_correct")
    private Boolean isCorrect;

    @Column("created_at")
    private LocalDateTime createdAt;
}
