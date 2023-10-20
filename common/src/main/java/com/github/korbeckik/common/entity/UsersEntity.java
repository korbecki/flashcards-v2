package com.github.korbeckik.common.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

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

    @Transient
    private List<RolesEntity> roles = new ArrayList<>();
}
