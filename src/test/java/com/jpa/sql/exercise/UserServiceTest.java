package com.jpa.sql.exercise;

import com.jpa.sql.exercise.entities.Document;
import com.jpa.sql.exercise.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    private List<User> expected = Arrays.asList(new User(6, "L"));

    @Autowired
    private UserService userService;

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
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

    @Test
    @Sql(scripts = "classpath:populateDBdocuments.sql")
    void saveUserWithDocuments () {
        User expectedUser = User.builder()
                .name("A")
                .manager(null)
                .documents(Arrays.asList(Document.builder().id(1).build(),
                        Document.builder().id(2).build()))
                .build();

        User user = userService.saveUser(expectedUser);
        assertNotNull(user);
    }

    private boolean equalsList(List<User> users) {

        if (expected.size() != users.size()) return false;

        for (User user : users) {
            if (!expected.contains(user)) return false;
        }
        return true;
    }
}