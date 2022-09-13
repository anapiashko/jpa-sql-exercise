package com.jpa.sql.exercise;

import com.jpa.sql.exercise.entities.Document;
import com.jpa.sql.exercise.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserCriteriaRepository {

    @Query(value = "select u2.id,u2.name,u2.manager_id,count(u2.id) from users u1 " +
            "inner join users u2 on u1.manager_id = u2.id " +
            "group by u2.id having count(u2.id) >= 3;",
            nativeQuery = true)
    List<User> getManagersThatHaveMoreThan3UsersUnderControlSql();

    @Query(value = "select u2 " +
            "from User u1 " +
            "join User u2 on u1.manager.id=u2.id " +
            "group by u2.id having count(u2.id)>=3")
    List<User> getManagersThatHaveMoreThan3UsersUnderControlJpql();

    @Query(value = "select u.documents " +
            "from User u " +
            "where u.id = :id")
    List<Document> getDocumentsById(Integer id);

    @Query(value = "select u " +
            "from User u " +
            "left join fetch u.documents " +
            "where u.id = :id")
    User getUserWithDocuments(Integer id);

    @Query(value = "select u.documents " +
            "from User u " +
            "where u.id = :id and expiration_date > CURRENT_DATE")
    List<Document> getValidDocumentsByUserId(Integer id);
}
