package com.example.demo.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonResponse {

    Long person_id;
    String name;
    Integer age;
    String email;
    String address;

    public PersonResponse(Long person_id, String name) {
        this.person_id = person_id;
        this.name = name;
    }
}
