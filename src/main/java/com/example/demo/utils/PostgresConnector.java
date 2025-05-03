package com.example.demo.utils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostgresConnector {

    @Value("${DB_URL}")
    String URL;

    @Value("${DB_USERNAME}")
    String USER;

    @Value("${DB_PASSWORD}")
    String PASSWORD;

    public Connection connect() {
        Connection connection = null;
        try {
            // Не обязательно с современных JDK, но можно явно загрузить драйвер
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Успешное подключение к PostgreSQL!");

        } catch (ClassNotFoundException e) {
            System.err.println("❌ PostgreSQL JDBC Driver не найден!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Ошибка подключения к БД!");
            e.printStackTrace();
        }

        return connection;
    }
}