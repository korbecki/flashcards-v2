package com.github.korbeckik.service.integration


import com.github.korbeckik.service.dto.response.FlashcardsListResponse
import com.github.korbeckik.service.dto.response.PagesListResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.test.web.reactive.server.WebTestClient

class PagesSpecification extends FlashcardsSpecification {

    @Autowired
    private WebTestClient webTestClient


    def "GET /pages/{flashcardId} endpoint should return 2xx status"() {
        given: "FlashcardsListResponse.class"
        FlashcardsListResponse response = webTestClient.get()
                .uri("/flashcards/list")
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(FlashcardsListResponse.class)
                .returnResult()
                .getResponseBody()
                .get(0)

        expect: "status 2xx"
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/pages/{flashcardId}")
                        .build(response.getId()))
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(PagesListResponse.class)
                .hasSize(2)
    }

    def "GET /pages/{flashcardId} endpoint should return 2xx status and empty body"() {
        expect: "status 2xx and empty body"
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/pages/{flashcardId}")
                        .build(100))
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(PagesListResponse.class)
                .hasSize(0)
    }

}
