package com.jpa.sql.exercise.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jpa.sql.exercise.listeners.UserEventListener;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(UserEventListener.class)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<>();

    @OneToOne
    private Address address;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }

    public void addDocument(Document child) {
        child.setUser(this);
        this.documents.add(child);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
