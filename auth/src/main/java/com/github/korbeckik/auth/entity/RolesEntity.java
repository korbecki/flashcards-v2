package com.github.korbeckik.auth.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("roles")
public class RolesEntity {
    @Id
    @Column("role_id")
    private Long id;

    @Column("name")
    private String name;
}
