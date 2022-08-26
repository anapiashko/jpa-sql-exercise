package com.jpa.sql.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getManagersThatHaveMoreThan3UsersUnderControlSql() {
        return userRepository.getManagersThatHaveMoreThan3UsersUnderControlSql();
    }

    public List<User> getManagersThatHaveMoreThan3UsersUnderControlJpql() {
        return userRepository.getManagersThatHaveMoreThan3UsersUnderControlJpql();
    }

    public List<User> getManagersThatHaveMoreThan3UsersUnderControlCriteriaApi() {
        return userRepository.getManagersThatHaveMoreThan3UsersUnderControlCriteriaApi();
    }
}
