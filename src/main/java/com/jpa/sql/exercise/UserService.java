package com.jpa.sql.exercise;

import com.jpa.sql.exercise.entities.Document;
import com.jpa.sql.exercise.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User findUserWithDocumentsById(Integer userId) {
        return userRepository.getUserWithDocuments(userId);
    }

    public List<User> getManagersThatHaveMoreThan3UsersUnderControlSql() {
        return userRepository.getManagersThatHaveMoreThan3UsersUnderControlSql();
    }

    public List<User> getManagersThatHaveMoreThan3UsersUnderControlJpql() {
        return userRepository.getManagersThatHaveMoreThan3UsersUnderControlJpql();
    }

    public List<User> getManagersThatHaveMoreThan3UsersUnderControlCriteriaApi() {
        return userRepository.getManagersThatHaveMoreThan3UsersUnderControlCriteriaApi();
    }

    public User saveUser (User user) {
        return userRepository.save(user);
    }

    public List<Document> getDocumentsByUserId (Integer userId) {
        return userRepository.getDocumentsById(userId);
    }

    public User addDocumentToUser(Integer userId, Document document) {
        User user = findUserWithDocumentsById(userId);
        if (user.getDocuments() == null) {
            user.setDocuments(new ArrayList<>(Arrays.asList(document)));
        } else {
            user.getDocuments().add(document);
        }
        return userRepository.save(user);
    }
}
