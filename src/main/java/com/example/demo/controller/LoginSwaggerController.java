package com.example.demo.controller;

import com.example.demo.dto.PersonRequestDTO;
import com.example.demo.service.PeopleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Tag(name = "LoginSwaggerController", description = "API for login")
public class LoginSwaggerController {

    final PeopleService peopleService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody PersonRequestDTO request) {
        return peopleService.login(request.getEmail(), request.getPassword())
                .map(dto -> ResponseEntity.ok(Map.of("status", "OK", "person", dto)))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "UNAUTHORIZED")));
    }
}


//    @Operation(summary = "Login with email and password")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Login successful"),
//            @ApiResponse(responseCode = "401", description = "Unauthorized")
//    })