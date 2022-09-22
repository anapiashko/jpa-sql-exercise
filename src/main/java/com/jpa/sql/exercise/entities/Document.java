package com.jpa.sql.exercise.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(DocumentEventListener.class)
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private LocalDate issueDate;

    private LocalDate expirationDate;

    private DocumentType documentType;

    @JsonBackReference
    @ManyToOne
    private User user;

    @Transient
    private Boolean valid;
}
