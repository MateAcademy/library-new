package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonFormDTO {

    Long id;

    String mediaId;

    @NotBlank
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @NotNull
    @Min(value = 0, message = "Age should be greater then 0")
    @Max(value = 120, message = "Age should be less then 120")
    private Integer age;

    @NotEmpty(message = "Email should not be empty")
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @NotEmpty(message = "Password should not be empty")
    private String password;

    @NotBlank
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 10, max = 50, message = "Address should be between 2 and 30 characters")
    @Pattern(
            regexp = "^[\\p{L}\\-]+, [\\p{L}\\- ]+, \\d{6}$",
            message = "Адрес должен быть в формате: 'Страна, Город, 123456'"
    )
    private String address;
}