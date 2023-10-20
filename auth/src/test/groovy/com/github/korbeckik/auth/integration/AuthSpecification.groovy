package com.github.korbeckik.auth.integration

import com.github.korbeckik.auth.dto.request.LoginRequest
import com.github.korbeckik.auth.dto.request.RegisterRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Shared

import java.util.function.Consumer

class AuthSpecification extends BaseSpecification {

    @Autowired
    private WebTestClient webTestClient

    @Shared
    WebTestClient.ResponseSpec loginResponse

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
        when: "status 2xx and Authorization header"
        def response = webTestClient.post()
                .uri("/login")
                .bodyValue(registerRequest)
                .exchange()
        loginResponse = response
        then:
        response.expectStatus()
        .is2xxSuccessful()
        .expectHeader()
        .exists(HttpHeaders.AUTHORIZATION)
    }

    def "/test endpoint without authorization header should return 401 status"() {
        expect: "status 401"
        webTestClient.get()
                .uri("/test")
                .exchange()
                .expectStatus()
                .is4xxClientError()
    }

    def "/test endpoint with authorization header should return 200 status"() {
        given:"Authorization header"
        String jwtToken = loginResponse.returnResult(ResponseEntity).getResponseHeaders().get(HttpHeaders.AUTHORIZATION)
        expect: "status 200"
        webTestClient.get()
                .uri("/test")
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
    }


}
