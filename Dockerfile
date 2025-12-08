# Multi-stage build для оптимизации размера образа

# Стадия 1: Сборка приложения
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Копируем pom.xml и загружаем зависимости (кэшируется)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем исходный код и собираем приложение
COPY src ./src
RUN mvn clean package -DskipTests -B

# Стадия 2: Запуск приложения
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Создаем группу и пользователя
RUN addgroup -S spring && adduser -S spring -G spring

# Создаем директорию для логов и отдаём её пользователю spring
RUN mkdir -p /app/logs && chown -R spring:spring /app

# Теперь переключаемся на непривилегированного пользователя
USER spring:spring

# Копируем JAR из стадии сборки
COPY --from=build /app/target/library-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
