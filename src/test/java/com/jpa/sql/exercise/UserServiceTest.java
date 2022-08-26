package com.jpa.sql.exercise;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    private List<User> expected = Arrays.asList(new User("6", "L"));

    @Autowired
    private UserService userService;

    @Test
    void getManagersThatHaveMoreThan3UsersUnderControlSql() {
        List<User> users = userService.getManagersThatHaveMoreThan3UsersUnderControlSql();
        assertEquals(1, users.size());
        assertTrue(equalsList(users));
    }

    @Test
    void getManagersThatHaveMoreThan3UsersUnderControlJpql() {
        List<User> users = userService.getManagersThatHaveMoreThan3UsersUnderControlJpql();
        assertEquals(1, users.size());
        assertTrue(equalsList(users));
    }

    @Test
    void getManagersThatHaveMoreThan3UsersUnderControlCriteriaApi() {
        List<User> users = userService.getManagersThatHaveMoreThan3UsersUnderControlCriteriaApi();
        assertEquals(1, users.size());
        assertTrue(equalsList(users));
    }

    private boolean equalsList(List<User> users) {

        if (expected.size() != users.size()) return false;

        for (User user : users) {
            if (!expected.contains(user)) return false;
        }
        return true;
    }
}