package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LibraryShortDto {
    Long libraryId;
    String name;
    String address;
}