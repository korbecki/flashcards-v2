package com.github.korbeckik.mail.entity;

public enum Role {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String name;

    Role(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
