# SafeDrive-Service 

## Description
SafeDrive is a Spring Boot application designed to provide secure and efficient driving services. It leverages various technologies such as Spring Data JPA, JWT for security, and Google Maps API for location services.

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- Maven
- MySQL
- JWT (JSON Web Tokens)
- Google Maps API

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6.0 or higher
- MySQL database

### Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/safeDrive-service.git
    cd safeDrive-service
    ```

2. Configure the database:
    - Update the `src/main/resources/application.properties` file with your database credentials and other configuration details.

3. Build the project:
    ```sh
    mvn clean install
    ```

4. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## Configuration
The application can be configured using the `application.properties` file located in the `src/main/resources` directory. Key configuration properties include:

- `spring.datasource.url`: URL of the MySQL database.
- `spring.datasource.username`: Database username.
- `spring.datasource.password`: Database password.
- `authorization.jwt.secret`: Secret key for JWT.
- `google.api.key`: API key for Google Maps.

## Usage
Once the application is running, you can access the API documentation at `http://localhost:8080/swagger-ui.html`.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/)
- [Google Maps API](https://developers.google.com/maps/documentation)