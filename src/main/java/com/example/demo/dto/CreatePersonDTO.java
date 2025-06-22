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
public class CreatePersonDTO {

    @NotBlank
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    String name;

    @NotNull
    @Min(value = 0, message = "Age should be greater then 0")
    @Max(value = 120, message = "Age should be less then 120")
    Integer age;

    @NotEmpty(message = "Email should not be empty")
    @Email
    @NotBlank
    String email;

    @NotBlank
    @NotEmpty(message = "Password should not be empty")
    String password;

    @NotBlank
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 10, max = 50, message = "Address should be between 2 and 30 characters")
    @Pattern(
            regexp = "^[\\p{L}\\-]+, [\\p{L}\\- ]+, \\d{6}$",
            message = "Please provide the address in the format: 'Country, City, 123456'."
    )
    String address;
}