package com.github.korbeckik.service.integration

import com.github.korbeckik.common.config.BearerToken
import com.github.korbeckik.common.dto.UserInfoUserDetails
import com.github.korbeckik.service.dto.request.PageDto
import com.github.korbeckik.service.dto.request.SaveFlashardsRequest
import com.github.korbeckik.service.dto.response.FlashcardsListResponse
import com.github.korbeckik.service.dto.response.PagesListResponse
import com.github.korbeckik.service.service.FlashcardsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.TestSecurityContextHolder
import org.springframework.security.test.context.support.ReactorContextTestExecutionListener
import org.springframework.test.context.TestExecutionListener
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ser.Serializers

class PagesSpecification extends BaseSpecification {

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
        flashcardsService.saveFlashcards(new SaveFlashardsRequest("name", "desc", "icon", Boolean.TRUE, List.of(pageDto, pageDto))).block()
    }

    def "GET /pages/{flashcardId} endpoint should return 2xx status"() {
        given:
        saveFlashcards()

        expect:
        FlashcardsListResponse response = flashcardsService.getFlashcards(null).blockFirst()
        and: "status 2xx"
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
        given:
        saveFlashcards()

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
