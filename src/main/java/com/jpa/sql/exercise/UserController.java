package com.jpa.sql.exercise;

import com.jpa.sql.exercise.entities.Document;
import com.jpa.sql.exercise.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getU/{id}")
    public User getU (@PathVariable Integer id) {
        return userService.findUserById(id);
    }

    @PostMapping("/save")
    public User save () {
        User user = User.builder()
                .name("P")
                .documents(new ArrayList<>())
                .build();
        user.addDocument(Document.builder().expirationDate(LocalDate.of(2032,8,31)).build());
        user.addDocument(Document.builder().expirationDate(LocalDate.of(2027,8,30)).build());
        user.addDocument(Document.builder().expirationDate(LocalDate.of(2025,8,29)).build());
        user.addDocument(Document.builder().expirationDate(LocalDate.of(2012,8,31)).build());

        User user1 = userService.saveUser(user);
        return user1;
    }

    @PostMapping("/saveAll")
    List<User> saveAll () {
        User user = User.builder()
                .name("P")
                .documents(new ArrayList<>())
                .build();
        user.addDocument(Document.builder().expirationDate(LocalDate.of(2032,8,31)).build());
        user.addDocument(Document.builder().expirationDate(LocalDate.of(2027,8,30)).build());
        user.addDocument(Document.builder().expirationDate(LocalDate.of(2025,8,29)).build());
        user.addDocument(Document.builder().expirationDate(LocalDate.of(2012,8,31)).build());

        User user1 = User.builder()
                .name("P1")
                .documents(new ArrayList<>())
                .build();
        user1.addDocument(Document.builder().expirationDate(LocalDate.of(2032,8,31)).build());
        user1.addDocument(Document.builder().expirationDate(LocalDate.of(2027,8,30)).build());

        List<User> users = userService.saveAll(List.of(user, user1));

        return users;
    }
}
