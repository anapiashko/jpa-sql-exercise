package com.jpa.sql.exercise;

import com.jpa.sql.exercise.entities.Document;
import com.jpa.sql.exercise.entities.DocumentType;
import com.jpa.sql.exercise.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
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
    void getDocumentsByUserId() {
        User expectedUser = User.builder()
                .name("A")
                .documents(new ArrayList<>())
                .build();
        expectedUser.addDocument(Document.builder().documentType(DocumentType.PASSPORT).build());
        expectedUser.addDocument(Document.builder().documentType(DocumentType.DRIVING_LICENSE).build());
        expectedUser.addDocument(Document.builder().documentType(DocumentType.INSURANCE).build());

        User user = userService.saveUser(expectedUser);

        List<Document> documents = userService.getDocumentsByUserId(user.getId());
        assertEquals(3, documents.size());
        assertEquals(expectedUser.getId(), documents.get(0).getUser().getId());
    }

    @Test
    void addDocumentsToUser() {

        // save user without documents
        User expectedUser = User.builder()
                .name("A")
                .build();
        User user = userService.saveUser(expectedUser);

        User foundUser = userService.findUserWithDocumentsById(user.getId());

        // adding the document to the user
        Document newDocument1 = Document.builder()
                .documentType(DocumentType.INSURANCE)
                .issueDate(LocalDate.of(2025,8, 29))
                .expirationDate(LocalDate.of(2030, 8,29))
                .user(foundUser)
                .build();

        User userWithNewDocument = userService.addDocumentToUser(foundUser.getId(), newDocument1);

        assertEquals(user.getId(), userWithNewDocument.getId());
        assertEquals(1, userWithNewDocument.getDocuments().size());

        Document newDocument2 = Document.builder()
                .documentType(DocumentType.PASSPORT)
                .issueDate(LocalDate.of(2025,8, 29))
                .expirationDate(LocalDate.of(2030, 8,29))
                .user(userWithNewDocument)
                .build();

        User userWithTwoDocuments = userService.addDocumentToUser(foundUser.getId(), newDocument2);

        List<Document> documents = userService.getDocumentsByUserId(userWithTwoDocuments.getId());

        assertEquals(user.getId(), userWithNewDocument.getId());
        assertEquals(2, documents.size());
    }

    @Test
    void saveUserWithDocuments () {
        User expectedUser = User.builder()
                .name("A")
                .manager(null)
                .documents(new ArrayList<>())
                .build();
        expectedUser.addDocument(Document.builder().documentType(DocumentType.PASSPORT).build());
        expectedUser.addDocument(Document.builder().documentType(DocumentType.DRIVING_LICENSE).build());

        User user = userService.saveUser(expectedUser);

        assertNotNull(user);
        assertNotNull(user.getId());
    }

    @Test
    void getValidDocumentsByUserId () {
        User user = User.builder()
                .name("P")
                .documents(new ArrayList<>())
                .build();
        user.addDocument(Document.builder().expirationDate(LocalDate.of(2032,8,31)).build());
        user.addDocument(Document.builder().expirationDate(LocalDate.of(2027,8,30)).build());
        user.addDocument(Document.builder().expirationDate(LocalDate.of(2025,8,29)).build());
        user.addDocument(Document.builder().expirationDate(LocalDate.of(2012,8,31)).build());

        User savedUser = userService.saveUser(user);

        List<Document> validDocuments = userService.getValidDocumentsByUserId(savedUser.getId());

        assertEquals(3, validDocuments.size());
    }

    private <T> boolean equalsExpectedList(List<T> expectedElements, List<T> elements) {

        if (expectedElements.size() != elements.size()) return false;

        for (T element : elements) {
            if (!expectedElements.contains(element)) return false;
        }
        return true;
    }
}