package com.company.aggregator;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@ActiveProfiles("integration-test")
@SpringBootTest(classes = {UsersApplication.class})
public class UserControllerIntegrationTest {

    @Autowired
    private DataSourceConfig dataSourceConfig;

    @Autowired
    private TestDataSourceConfig testDataSourceConfig;

    @Autowired
    private UserController userController;

    @BeforeEach
    public void setUp() {

        /*dataSourceConfig.getSources().stream().map(dataSourceProperties -> {
            testDataSourceConfig.getSources().stream().map(dataSourceProperties.getName().equals(dataSourceConfig.getSources().))
        });*/
        testDataSourceConfig.getSources().forEach(source -> {
            final var container = new PostgreSQLContainer<>(source.getImage())
                    .withDatabaseName(source.getDbName())
//                    .withUsername(.getUser())
//                    .withPassword(source.getPassword())
                    .withInitScript(source.getInitSchema());
            container.start();
            assertThat(container.isRunning()).isTrue();
            //TODO Union the dataSourceConfig with the testData
//            source.setUrl(container.getJdbcUrl());
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

