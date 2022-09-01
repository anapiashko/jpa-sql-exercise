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

    private final List<User> expected = Arrays.asList(new User(6, "L"));

    @Autowired
    private UserService userService;

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    void getManagersThatHaveMoreThan3UsersUnderControlSql() {
        List<User> users = userService.getManagersThatHaveMoreThan3UsersUnderControlSql();
        assertEquals(1, users.size());
        assertTrue(equalsExpectedList(expected, users));
    }

    @Test
    void getManagersThatHaveMoreThan3UsersUnderControlJpql() {
        List<User> users = userService.getManagersThatHaveMoreThan3UsersUnderControlJpql();
        assertEquals(1, users.size());
        assertTrue(equalsExpectedList(expected, users));
    }

    @Test
    void getManagersThatHaveMoreThan3UsersUnderControlCriteriaApi() {
        List<User> users = userService.getManagersThatHaveMoreThan3UsersUnderControlCriteriaApi();
        assertEquals(1, users.size());
        assertTrue(equalsExpectedList(expected, users));
    }

    @Test
    @Sql(scripts = "classpath:populateDBdocuments.sql")
    void getDocumentsByUserId() {
        User expectedUser = User.builder()
                .name("A")
                .manager(null)
                .documents(Arrays.asList(Document.builder().id(1).build(),
                        Document.builder().id(2).build(),
                        Document.builder().id(3).build()))
                .build();
        User user = userService.saveUser(expectedUser);

        List<Document> documents = userService.getDocumentsByUserId(user.getId());
        assertEquals(3, documents.size());
        assertTrue(equalsExpectedList(documents, documents));
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

    private <T> boolean equalsExpectedList(List<T> expectedElements, List<T> elements) {

        if (expectedElements.size() != elements.size()) return false;

        for (T element : elements) {
            if (!expectedElements.contains(element)) return false;
        }
        return true;
    }
}