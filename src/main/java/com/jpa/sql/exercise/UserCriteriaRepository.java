package com.jpa.sql.exercise;

import com.jpa.sql.exercise.entities.User;

import java.util.List;

public interface UserCriteriaRepository {

    List<User> getManagersThatHaveMoreThan3UsersUnderControlCriteriaApi();
}
