package com.jpa.sql.exercise.listeners;

import com.jpa.sql.exercise.entities.User;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
public class UserEventListener {

    @PrePersist
    public void logNewUserAttempt(User user) {
        log.info("Attempting to add new user with username: " + user.getName());
    }

    @PostPersist
    public void logNewUserAdded(User user) {
        log.info("Added user '" + user.getName() + "' with ID: " + user.getId());
    }

    @PreRemove
    public void logUserRemovalAttempt(User user) {
        log.info("Attempting to delete user: " + user.getName());
    }

    @PostRemove
    public void logUserRemoval(User user) {
        log.info("Deleted user: " + user.getName());
    }

    @PreUpdate
    public void logUserUpdateAttempt(User user) {
        log.info("Attempting to update user: " + user.getName());
    }

    @PostUpdate
    public void logUserUpdate(User user) {
        log.info("Updated user: " + user.getName());
    }

    @PostLoad
    public void logUserLoad(User user) {
        log.info("PostLoad user: " + user.getName() + " " + user.getId());
    }
}
