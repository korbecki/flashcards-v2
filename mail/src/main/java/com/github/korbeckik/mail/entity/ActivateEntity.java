package com.github.korbeckik.mail.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("activate")
public class ActivateEntity {

    @Id
    @Column("activate_id")
    private Long id;

    @Column("is_activated")
    private Boolean isActivated;

    @Column("code")
    private String code;

    @Column("status")
    private ActivateStatus status;

    @Column("expired_at")
    private LocalDateTime expiredAt;

    public void markAsSent(String uuid) {
        this.code = uuid;
        this.status=ActivateStatus.SENT;
        this.expiredAt = LocalDateTime.now().plusDays(1L);
    }
}
