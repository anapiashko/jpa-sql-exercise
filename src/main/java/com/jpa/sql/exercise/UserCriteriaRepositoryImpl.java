package com.jpa.sql.exercise;

import com.jpa.sql.exercise.entities.User;
import com.jpa.sql.exercise.entities.User_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class UserCriteriaRepositoryImpl implements UserCriteriaRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<User> getManagersThatHaveMoreThan3UsersUnderControlCriteriaApi() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);

        Root<User> user1 = criteriaQuery.from(User.class);

        Join<User,User> user2 = user1.join(User_.manager, JoinType.INNER);

        criteriaQuery.multiselect(user2.get(User_.id), user2.get(User_.name))
                .groupBy(user2.get(User_.id))
                .having(cb.greaterThanOrEqualTo(cb.count(user1.get(User_.id)), 3L))
        ;

        List<User> users = entityManager.createQuery(criteriaQuery).getResultList();

        return users;
    }
}
