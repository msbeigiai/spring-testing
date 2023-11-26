package com.msbeigi.sprintboot.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractContainerBestTest {

    static final PostgreSQLContainer POSTGRE_SQL_CONTAINER;
    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer("postgres:15.3");
        POSTGRE_SQL_CONTAINER.start();
    }

    protected static final String BASE_URI = "/api/employees";

    @DynamicPropertySource
    public static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
    }
}
