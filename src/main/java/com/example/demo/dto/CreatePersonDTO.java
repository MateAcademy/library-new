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

    @NotBlank(message = "Name should not be blank")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    String name;

    @NotNull(message = "Age cannot be null")
    @Min(value = 1, message = "Age should be greater than 0")
    @Max(value = 120, message = "Age should be less than or equal to 120")
    Integer age;

    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email should be a valid format")
    String email;

    @NotBlank(message = "Password should not be blank")
    String password;

    @NotBlank(message = "Address should not be blank")
    @Size(min = 10, max = 100, message = "Address should be between 10 and 100 characters")
    @Pattern(
        regexp = "^[\\p{L}\\-]+, [\\p{L}\\- ]+, \\d{6}$",
        message = "Please provide the address in the format: 'Country, City, 123456'."
    )
    String address;
}
