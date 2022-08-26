package com.jpa.sql.exercise;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserDto {
    @Id
    private String idU;
    private String nameU;
    private Long countU;

}
