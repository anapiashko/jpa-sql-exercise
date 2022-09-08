package com.jpa.sql.exercise.listeners;

import com.jpa.sql.exercise.entities.Document;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
public class DocumentEventListener {

    @PrePersist
    public void logNewDocumentAttempt(Document document) {
        log.info("Attempting to add new document : " + document);
    }

    @PostPersist
    public void logNewDocumentAdded(Document document) {
        log.info("Added document '" + document);
    }

    @PreRemove
    public void logDocumentRemovalAttempt(Document document) {
        log.info("Attempting to delete document: " + document);
    }

    @PostRemove
    public void logDocumentRemoval(Document document) {
        log.info("Deleted document: " + document);
    }

    @PreUpdate
    public void logDocumentUpdateAttempt(Document document) {
        log.info("Attempting to update document: " + document);
    }

    @PostUpdate
    public void logDocumentUpdate(Document document) {
        log.info("Updated document: " + document);
    }

    @PostLoad
    public void logDocumentLoad(Document document) {
        log.info("PostLoad document: " + document);
    }
}
