package com.jpa.sql.exercise;

import com.jpa.sql.exercise.entities.Document;
import com.jpa.sql.exercise.entities.DocumentType;
import com.jpa.sql.exercise.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
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
    @Sql(scripts = "classpath:populateDB.sql")
    void getManagersThatHaveMoreThan3UsersUnderControlJpql() {
        List<User> users = userService.getManagersThatHaveMoreThan3UsersUnderControlJpql();
        assertEquals(1, users.size());
        assertTrue(equalsExpectedList(expected, users));
    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
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
    }

    @Test
    void addDocumentsToUser() {

        // save the user itself
        User expectedUser = User.builder()
                .name("A")
                .manager(null)
                .documents(null)
                .build();
        User user = userService.saveUser(expectedUser);

        User foundUser = userService.findUserWithDocumentsById(user.getId());

        // adding the document to the user
        Document newDocument1 = Document.builder()
                .documentType(DocumentType.INSURANCE)
                .issueDate(LocalDate.of(2025,8, 29))
                .expirationDate(LocalDate.of(2030, 8,29))
                .build();

        User userWithNewDocument = userService.addDocumentToUser(foundUser.getId(), newDocument1);

        assertEquals(user.getId(), userWithNewDocument.getId());
        assertEquals(1, userWithNewDocument.getDocuments().size());

        Document newDocument2 = Document.builder()
                .documentType(DocumentType.PASSPORT)
                .issueDate(LocalDate.of(2025,8, 29))
                .expirationDate(LocalDate.of(2030, 8,29))
                .build();

        User userWithTwoDocuments = userService.addDocumentToUser(foundUser.getId(), newDocument2);

        List<Document> documents = userService.getDocumentsByUserId(userWithTwoDocuments.getId());

        assertEquals(user.getId(), userWithNewDocument.getId());
        assertEquals(2, documents.size());
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