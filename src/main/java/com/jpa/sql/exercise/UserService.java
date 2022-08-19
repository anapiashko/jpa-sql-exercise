package com.jpa.sql.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getManagersThatHaveMoreThan3UsersUnderControl() {
        return userRepository.getManagersThatHaveMoreThan3UsersUnderControl();
    }
}
