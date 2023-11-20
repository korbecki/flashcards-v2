package com.github.korbeckik.service.integration

import com.github.korbeckik.common.dto.UserInfoUserDetails
import com.github.korbeckik.service.dto.request.PageDto
import com.github.korbeckik.service.dto.request.SaveFlashardsRequest
import com.github.korbeckik.service.dto.response.FlashcardsListResponse
import com.github.korbeckik.service.service.FlashcardsService
import io.micrometer.common.util.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.test.context.TestSecurityContextHolder
import org.springframework.security.test.context.support.ReactorContextTestExecutionListener
import org.springframework.test.context.TestExecutionListener
import org.springframework.test.web.reactive.server.WebTestClient

class FlashcardsSpecification extends BaseSpecification {

    @Autowired
    private WebTestClient webTestClient

    @Autowired
    private FlashcardsService flashcardsService;

    private TestExecutionListener reactorContextTestExecutionListener = new ReactorContextTestExecutionListener();

    private void saveFlashcards(){
        UserInfoUserDetails userDetails = new UserInfoUserDetails(user)
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("USER")
        TestSecurityContextHolder.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), List.of(simpleGrantedAuthority)))
        reactorContextTestExecutionListener.beforeTestMethod(null)

        PageDto pageDto = new PageDto("question", "questionImage", "response", "responseImage")
        flashcardsService.saveFlashcards(new SaveFlashardsRequest("Polish", "desc", "icon", Boolean.TRUE, List.of(pageDto, pageDto))).block()
    }

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

    def "GET /flashcards/list endpoint should return 2xx status and not empty list"() {
        given:
        saveFlashcards()
        expect: "status 2xx"
        webTestClient.get()
                .uri("/flashcards/list")
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(FlashcardsListResponse.class)
                .returnResult().getResponseBody().size() > 0
    }

    def "GET /flashcards/list endpoint with http param should return 2xx status and not empty list"() {
        given:
        saveFlashcards()
        expect: "status 2xx and list with 1 element"
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
                .returnResult()
                .getResponseBody()
                .size() > 0
    }

    def "GET /flashcards/list endpoint with http param should return 2xx status andempty list"() {
        given:
        saveFlashcards()
        expect: "status 2xx and list with 0 element"
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flashcards/list")
                        .queryParam("name", "De")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(FlashcardsListResponse.class)
                .hasSize(0)
    }

}
