package com.github.korbeckik.auth.dto.request;

public record RegisterRequest(String name, String surname, String userName, String password, String email) {
}
