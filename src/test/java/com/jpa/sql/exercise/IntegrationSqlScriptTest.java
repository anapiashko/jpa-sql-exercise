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
class IntegrationSqlScriptTest {

    @Autowired
    private UserRepository userRepository;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:14")
                    .withDatabaseName("mydb")
                    .withUsername("myuser")
                    .withPassword("mypass")
            ;

//    static {
//        postgreSQLContainer.start();
//        assertTrue(postgreSQLContainer.isRunning());
//    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);

    }

    @Test
    @Sql(scripts = "classpath:populateDB.sql")
    void animalsCountShouldBeCorrect() {
        long count = userRepository.count();
        userRepository.findById(1);
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