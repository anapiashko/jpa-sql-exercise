package com.jpa.sql.exercise;

import java.util.List;

public interface UserCriteriaRepository {

    List<User> getManagersThatHaveMoreThan3UsersUnderControlCriteriaApi();
}
