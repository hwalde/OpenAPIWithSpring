# OpenAPI Spring Project

This project demonstrates the integration of OpenAPI with Spring Boot, showcasing how to generate server-side code from an OpenAPI specification and implement the delegate pattern for API implementation.

## Technologies Used

- **Spring Boot 3.4.5**: Modern Java framework for building web applications
- **OpenAPI Generator**: Tool for generating server stubs from OpenAPI specifications
- **Springdoc OpenAPI**: Integration for Swagger UI documentation
- **Maven**: Build and dependency management tool

## Related Frontend Project

This backend project has a corresponding frontend implementation that demonstrates how to consume the API:

- **Repository**: [Frontend4OpenAPIWithSpring](https://github.com/hwalde/Frontend4OpenAPIWithSpring)
- **Features**:
    - Demonstrates how to consume the OpenAPI-generated backend
    - Shows how to integrate with the News API endpoints
    - Provides a complete end-to-end example of an OpenAPI-based application

The frontend project complements this backend by showing how clients can be built to interact with OpenAPI-defined services.

## Project Structure

The project is structured around the OpenAPI-first approach, where the API is defined in an OpenAPI specification file, and server code is generated from it.

### OpenAPI Specification

The API is defined in:
- `src/main/resources/news.yaml`: Contains the News API specification

### Generated Code

The OpenAPI Generator Maven plugin generates server stubs during the build process, including:
- API interfaces
- Model classes
- Controller interfaces

### Implementation

The application implements the generated interfaces using the delegate pattern:
- `NewsApiDelegateImpl`: Implements the News API endpoints

## OpenAPI Generator Maven Plugin

The OpenAPI Generator plugin is configured in the `pom.xml` file and runs during the Maven build process. It generates server-side code based on the OpenAPI specification.

```xml
<plugin>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>7.6.0</version>
    <configuration>
        <inputSpec>${project.basedir}/src/main/resources/news.yaml</inputSpec>
        <generatorName>spring</generatorName>
        <o>${project.build.directory}/generated-sources/openapi</o>
        <skipOverwrite>true</skipOverwrite>
        <ignoreFileOverride>${project.basedir}/.openapi-generator-ignore</ignoreFileOverride>
        <configOptions>
            <library>spring-boot</library>
            <useSpringBoot3>true</useSpringBoot3>
            <delegatePattern>true</delegatePattern>
            <interfaceOnly>false</interfaceOnly>
            <requestMappingMode>controller</requestMappingMode>
            <performBeanValidation>true</performBeanValidation>
        </configOptions>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Key configuration options:
- `inputSpec`: Path to the OpenAPI specification file
- `generatorName`: Specifies the generator to use (spring)
- `o`: Output directory for generated code
- `skipOverwrite`: Prevents overwriting existing files
- `delegatePattern`: Enables the delegate pattern for implementation

The plugin is executed during the Maven build process, specifically during the `generate-sources` phase.

## Delegate Pattern

The project uses the delegate pattern to implement the generated API interfaces. This pattern allows for separation of the API definition from its implementation:

1. The OpenAPI Generator creates interface controllers with the `@Api*` annotations
2. These interfaces define methods that delegate to an implementation class
3. You implement the delegate interface in your own code

Example implementation:

```java
@Component
public class NewsApiDelegateImpl implements NewsApiDelegate {

    @Override
    public ResponseEntity<List<NewsArticle>> newsGet(String category,
                                                     LocalDate publicationDate,
                                                     Integer limit,
                                                     Integer offset) {
        // Implementation logic here
        return ResponseEntity.ok(
            List.of(
                new NewsArticle()
                        .id(1L)
                        .title("Test 1")
                        .content("Content")
                        // ...other properties
            )
        );
    }

    @Override
    public ResponseEntity<NewsArticle> newsIdGet(Long id) {
        // Implementation logic here
        return ResponseEntity.ok(new NewsArticle()
                .id(1L)
                .title("Test - " + id)
                // ...other properties
        );
    }
}
```

Benefits of the delegate pattern:
- Clean separation of generated code and implementation
- Easier to maintain when regenerating code
- Better organization of business logic

## Component Scanning Configuration

To ensure that both the generated code and your implementation are properly recognized by Spring, the application is configured with specific component scanning:

```java
@SpringBootApplication
@ComponentScan(basePackages = {
        "de.entwickler.training.openapispring", // Your own code including delegates
        "org.openapitools.api",                // Generated API controllers
        "org.openapitools.configuration"       // Generated OpenAPI/Swagger configuration
})
public class OpenapiSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenapiSpringApplication.class, args);
    }
}
```

This configuration ensures that Spring can find and wire:
1. Your delegate implementations
2. The generated API controllers
3. The OpenAPI configuration classes

## Swagger UI

The project includes Springdoc OpenAPI UI, which provides a web interface to interact with the API. When the application is running, you can access the Swagger UI at:

```
http://localhost:8080/
```

The UI allows you to:
- View all available endpoints
- Test API calls directly from the browser
- See request/response models
- View API documentation

## Running the Application

To run the application:

1. Clone the repository
2. Build the project with Maven:
   ```
   ./mvnw clean install
   ```
3. Run the application:
   ```
   ./mvnw spring-boot:run
   ```
4. Access the Swagger UI at http://localhost:8080/

## Development Workflow

When developing with this approach:

1. Define or update your API in the OpenAPI specification file (`src/main/resources/news.yaml`)
2. Run the Maven build to generate the server stubs
3. Implement or update the delegate classes to provide the actual business logic
4. Run the application to test your implementation

This workflow ensures that your API implementation always matches the specification.

## Dependencies

Key dependencies in the project:

- Spring Boot Web: For building the REST API
- Spring Boot Validation: For request validation
- Jackson Databind Nullable: For handling nullable fields in the generated models
- Springdoc OpenAPI: For Swagger UI integration

## Conclusion

This project demonstrates a modern approach to API development using OpenAPI specifications and code generation. By defining the API first and generating the server stubs, you ensure consistency between your API documentation and implementation. Combined with the related frontend project, it provides a complete example of an API-first development approach from specification to full-stack implementation.
