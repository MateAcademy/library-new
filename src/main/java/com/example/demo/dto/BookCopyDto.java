package com.example.demo.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopyDto {

    Long bookCopyId;
    boolean isAvailable;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    PersonDto owner;

}
