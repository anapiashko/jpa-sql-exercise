package com.jpa.sql.exercise;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
