package com.github.korbeckik.auth.integration


import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseSpecification extends Specification {

    protected static PostgreSQLContainer<? extends PostgreSQLContainer> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.0"))
            .withDatabaseName("flashcards")
            .withPassword("postgres")
            .withUsername("postgres")

    static {
        postgreSQLContainer.start()
    }

    @DynamicPropertySource
    static void containerConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> postgreSQLContainer.getJdbcUrl().replace("jdbc", "r2dbc"))
        registry.add("spring.liquibase.url", () -> postgreSQLContainer.getJdbcUrl())
    }


}

