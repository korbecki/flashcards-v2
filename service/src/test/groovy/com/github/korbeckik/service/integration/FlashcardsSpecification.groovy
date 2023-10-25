package com.github.korbeckik.service.integration

import com.github.korbeckik.service.dto.request.PageDto
import com.github.korbeckik.service.dto.request.SaveFlashardsRequest
import com.github.korbeckik.service.dto.response.FlashcardsListResponse
import io.micrometer.common.util.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

class FlashcardsSpecification extends BaseSpecification {

    @Autowired
    private WebTestClient webTestClient


    def "JWT token should be generated"() {
        expect:
        StringUtils.isNotEmpty(jwtToken)
    }

    def "POST /flashcards endpoint should return 2xx status"() {
        given: "SaveFlashardsRequest.class"
        PageDto pageDto = new PageDto("cat", "catImage", "kot", "kotImage")
        SaveFlashardsRequest saveFlashardsRequest =
                new SaveFlashardsRequest("English", "English flashcards", "icon", Boolean.TRUE, List.of(pageDto, pageDto))
        expect: "status 2xx"
        webTestClient.post()
                .uri("/flashcards")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(saveFlashardsRequest)
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
    }

    def "GET /flashcards/list endpoint should return 2xx status and list with 1 elements"() {
        expect: "status 2xx and list with 1 element"
        webTestClient.get()
                .uri("/flashcards/list")
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(FlashcardsListResponse.class)
                .hasSize(1)
    }

    def "GET /flashcards/list endpoint with http param should return 2xx status and list with 1 elements"() {
        expect: "status 2xx and list with 1 element"
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flashcards/list")
                        .queryParam("name", "Eng")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(FlashcardsListResponse.class)
                .hasSize(1)
    }

    def "GET /flashcards/list endpoint with http param should return 2xx status and list with 0 element"() {
        expect: "status 2xx and list with 0 element"
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flashcards/list")
                        .queryParam("name", "Pol")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(FlashcardsListResponse.class)
                .hasSize(0)
    }

}
