package com.company.aggregator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("h2-test")
@SpringBootTest(classes = {UsersApplication.class})
public class UserControllerH2Test {

    @Autowired
    private UserController userController;

    @Test
    public void testGetUsers() {

        final var expectedUserJon = new User("example-user-id-1", "John", "jdoe", "Doe");
        final var expectedUserAlice = new User("example-ldap_login-1", "example-ldap_login-1", "Alice Johnson", "Manager");
        final var expectedUserHank = new User("user-mysql-id-1", "Hank", "hank_t", "Taylor");

        final var users = userController.getUsers();
        final String templatesAssertMessage = "Users does not contain %s";
        Assertions.assertAll(
                () -> Assertions.assertTrue(users.contains(expectedUserJon), String.format(templatesAssertMessage, expectedUserJon)),
                () -> Assertions.assertTrue(users.contains(expectedUserAlice), String.format(templatesAssertMessage,expectedUserAlice)),
                () -> Assertions.assertTrue(users.contains(expectedUserHank), String.format(templatesAssertMessage,expectedUserHank))
        );

    }

}

