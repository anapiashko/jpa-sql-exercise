package com.jpa.sql.exercise;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String idU;
    private String nameU;
    private Long countU;

}
