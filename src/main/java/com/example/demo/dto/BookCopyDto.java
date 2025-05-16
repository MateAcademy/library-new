package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCopyDto {

    Long bookCopyId;
    boolean isAvailable;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    PersonDto owner;
}
