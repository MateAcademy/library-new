package com.example.demo.model;

import java.time.LocalDateTime;

public interface AuthorEntityInterface {
    Long getCreatedBy();

    Long getUpdatedBy();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}