package com.example.demo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonResponseDTO {

    @NotNull(message = "ID must not be null")
    @Min(value = 1, message = "ID must be greater than 0")
    Long id;

    @NotBlank(message = "Name should not be empty")
    String mediaId;

    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    String name;

    @NotNull(message = "Возраст обязателен")
    @Min(value = 0, message = "Age should be greater then 0")
    @Max(value = 120, message = "Age should be less then 120")
    Integer age;

    @NotBlank(message = "Email should not be empty")
    @Email
    String email;

    @NotBlank(message = "Address should not be empty")
    @Size(min = 10, max = 50, message = "Address should be between 2 and 30 characters")
    @Pattern(
            regexp = "^[\\p{L}\\-]+, [\\p{L}\\- ]+, \\d{6}$",
            message = "Адрес должен быть в формате: 'Страна, Город, 123456'"
    )
    @Column(name = "address")
    String address;

}