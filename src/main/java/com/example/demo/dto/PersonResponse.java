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

    Long personId;
    String personMediaId;
    String name;
    Integer age;
    String email;
    String address;

    public PersonResponse(Long personId, String personMediaId, String name) {
        this.personId = personId;
        this.personMediaId = personMediaId;
        this.name = name;
    }
}
