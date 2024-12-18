package com.company.aggregator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Testcontainers
@ActiveProfiles("integration-test")
@SpringBootTest(classes = {UsersApplication.class})
public class UserControllerIntegrationTest {

    @Autowired
    private DataSourceConfig dataSourceConfig;

    @Autowired
    private UserController userController;


    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName("database1")
            .withUsername("testuser")
            .withPassword("testpass")
            .withInitScript("init-schema.sql");

    @Test
    void testGetUsers() throws Exception {
        assertThat(postgres.isRunning()).isTrue();

        userController.getUsers();


//        final ResultActions resultActions = mockMvc.perform(get("/api/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("John"))
//                .andExpect(jsonPath("$.username").value("jdoe"));
    }

}

