package com.github.korbeckik.common.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Table("users")
public class UsersEntity {

    @Id
    @Column("user_id")
    private Long id;

    @Column("name")
    private String name;

    @Column("surname")
    private String surname;

    @Column("user_name")
    private String userName;

    @Column("password")
    private String password;

    @Column("email")
    private String email;

    @Column("activate_id")
    private Long activateId;

    @Column("refresh_token")
    private String refreshToken;

    @Column("refresh_token_expiration")
    private LocalDateTime refreshTokenExpiration;

    @Transient
    private List<RolesEntity> roles = new ArrayList<>();

    public void createRefreshToken() {
        refreshToken = UUID.randomUUID().toString();
        refreshTokenExpiration = LocalDateTime.now().plusDays(1L);
    }
}
