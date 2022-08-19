package com.jpa.sql.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "select u2.id,u2.name,u2.manager_id,count(u2.id) from users u1 " +
            "inner join users u2 on u1.manager_id = u2.id " +
            "group by u2.id having count(u2.id) >= 3;",
            nativeQuery = true)
    List<User> getManagersThatHaveMoreThan3UsersUnderControl();
}
