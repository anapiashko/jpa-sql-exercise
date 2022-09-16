package com.jpa.sql.exercise;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class TestContainersTest extends AbstractApplicationTest{

    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    void countAllUsersInDB() {
        long count = userRepository.count();
        assertEquals(8, count);
    }

//    @Test
//    @Transactional
//    public void animalsShouldBeCorrect() {
//        List<User> animals = userRepository.findAll();
//        String[] actualAnimals = {"animal1", "animal2", "animal3"};
//        assertArrayEquals(actualAnimals, animals.stream().map(animal -> animal.getName()).toArray());
//    }
}