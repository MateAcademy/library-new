# EduCheck Core Service Guidelines
- If you don't know the answer just tell "I don't know". Do not fabricate information.

## Build/Test Commands

### Maven Wrapper (Preferred)
- Build: `./mvnw clean install`
- Run: `./mvnw spring-boot:run`
- Test: `./mvnw test`
- Single test: `./mvnw test -Dtest=TestClassName#methodName`
- Integration tests: `./mvnw failsafe:integration-test`
- Compile only: `./mvnw compile`

### Docker Maven (Alternative)
- Build: `docker run --rm -v "$(pwd)":/usr/src/app -v ~/.m2:/root/.m2 -w /usr/src/app maven:3-amazoncorretto-21 mvn clean install`
- Run: `docker run --rm -v "$(pwd)":/usr/src/app -v ~/.m2:/root/.m2 -w /usr/src/app -p 8080:8080 maven:3-amazoncorretto-21 mvn spring-boot:run`
- Test: `docker run --rm -v "$(pwd)":/usr/src/app -v ~/.m2:/root/.m2 -w /usr/src/app maven:3-amazoncorretto-21 mvn test`
- Single test: `docker run --rm -v "$(pwd)":/usr/src/app -v ~/.m2:/root/.m2 -w /usr/src/app maven:3-amazoncorretto-21 mvn test -Dtest=TestClassName#methodName`
- Integration tests: `docker run --rm -v "$(pwd)":/usr/src/app -v ~/.m2:/root/.m2 -w /usr/src/app maven:3-amazoncorretto-21 mvn failsafe:integration-test`
- Compile only: `docker run --rm -v "$(pwd)":/usr/src/app -v ~/.m2:/root/.m2 -w /usr/src/app maven:3-amazoncorretto-21 mvn -T 1C compile`

## Code Style Guidelines
- Java version: 21
- Packages: Follow `de.fwu.educheck.core.*` structure
- Class naming: PascalCase, descriptive nouns
- Method naming: camelCase, verb+noun
- Variables: camelCase, meaningful names
- Method calls within the same class should use `this.` prefix (e.g., `this.getUserById()` instead of `getUserById()`)
- Use Lombok annotations to reduce boilerplate
- Import ordering: Java, 3rd party, project classes
- Use `@Nullable` annotations from `org.jspecify.annotations` package when methods could have null parameters or return null values
- Proper exception handling with custom exceptions
- Service layer for business logic, controllers for API
- Use constructor injection for dependencies
- Use Spring annotations consistently (@Service, @Repository)
- Include comprehensive tests for new functionality
- Always use `final`

## Git Workflow
- Commit messages: descriptive, present tense
- Branch naming: feature/*, bugfix/*, etc.

## Project Structure
- Store prompts in `prompts` folder
- Hard wrap lines at 200 symbols.