package com.github.korbeckik.auth.integration

import com.github.korbeckik.auth.dto.request.LoginRequest
import com.github.korbeckik.auth.dto.request.RegisterRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.test.web.reactive.server.WebTestClient

class AuthSpecification extends BaseSpecification {

    @Autowired
    private WebTestClient webTestClient;

    def "/register endpoint should return 2xx status"() {
        given: "RegisterRequest.class"
        RegisterRequest registerRequest =
                new RegisterRequest("Jan", "Kowalski", "jan.kowalski", "password", "jan@kowalski.pl")
        expect: "status 2xx"
        webTestClient.post()
                .uri("/register")
                .bodyValue(registerRequest)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
    }

    def "/login endpoint should return 2xx status and Authorization header"() {
        given: "LoginRequest.class"
        LoginRequest registerRequest =
                new LoginRequest("jan@kowalski.pl", "password")
        expect: "status 2xx and Authorization header"
        webTestClient.post()
                .uri("/login")
                .bodyValue(registerRequest)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .exists(HttpHeaders.AUTHORIZATION)
    }
}
