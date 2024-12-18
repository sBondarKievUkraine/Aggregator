package com.company.aggregator;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.JdbcDatabaseContainer;
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

    @BeforeEach
    public void setUp() {
        dataSourceConfig.getSources().forEach(source -> {
            final var container = new PostgreSQLContainer<>(source.getStrategy())
                    .withDatabaseName(source.getName())
                    .withUsername(source.getUser())
                    .withPassword(source.getPassword())
                    .withInitScript(source.getUrl());
            container.start();
            assertThat(container.isRunning()).isTrue();
            source.setUrl(container.getJdbcUrl());
        });
    }

    @Test
    public void testGetUsers() throws Exception {

        final Collection<User> users = userController.getUsers();
        System.out.println(users);
//        final ResultActions resultActions = mockMvc.perform(get("/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("John"))
//                .andExpect(jsonPath("$.username").value("jdoe"));
    }

}

