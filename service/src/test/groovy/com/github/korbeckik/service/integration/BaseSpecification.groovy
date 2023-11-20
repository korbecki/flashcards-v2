package com.github.korbeckik.service.integration

import com.github.korbeckik.common.entity.UsersEntity
import com.github.korbeckik.common.repository.UsersRepository
import com.github.korbeckik.common.service.JWTService
import org.spockframework.spring.EnableSharedInjection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableSharedInjection
@EnableAspectJAutoProxy
abstract class BaseSpecification extends Specification {

    @Autowired
    @Shared
    private UsersRepository usersRepository

    @Autowired
    @Shared
    private JWTService jwtService

    @Shared
    protected UsersEntity user = new UsersEntity(name: "test", surname: "test", userName: "test_test", password: "1234", email: "test@test.com")

    @Shared
    protected String jwtToken

    public static PostgreSQLContainer<? extends PostgreSQLContainer> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.0"))
            .withDatabaseName("flashcards")
            .withPassword("postgres")
            .withUsername("postgres")
            .withReuse(false)

    static {
        postgreSQLContainer.start()
    }

    @DynamicPropertySource
    static void containerConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> postgreSQLContainer.getJdbcUrl().replace("jdbc", "r2dbc"))
        registry.add("spring.liquibase.url", () -> postgreSQLContainer.getJdbcUrl())
    }

    def setup() {
        println('run setupSpec()')
        Boolean userExists = usersRepository.existsByUserName(user.getUserName()).block()
        if(userExists == Boolean.FALSE) {
            usersRepository.save(user)
                    .log("User Saved")
                    .block()
        }


        jwtToken = jwtService.generateToken(user.getEmail())
    }
}

