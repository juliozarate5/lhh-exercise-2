# Microservice Consumer Token ViveLibre

## Overview
The following repo contains Microservice consumes external token service of VivaLibre

## Architecture

The application's architecture is **Hexagonal architecture**.
The following articles will explain the purpose and benefits of it, and how to configure it:
1. [Baeladung Hexagonal architecture](https://www.baeldung.com/hexagonal-architecture-ddd-spring)
2. [Medium Hands-on Hexagonal architecture](https://medium.com/javarevisited/hands-on-hexagonal-architecture-with-spring-boot-ca61f88bed8b)

This application has layers: application, domain & infrastructure.

## Design Patterns, Clean code & SOLID Principles

The application has been coded following best practices in code design and object-oriented programming, clean code, and SOLID principles. It also incorporates several design patterns, such as DTO, DAO, Repository, Builder, Singleton, and others.

## Requirements

1. Download & setup GIT for your OS: [GIT Download](https://git-scm.com/)
2. Download & setup Docker: [Docker Download](https://www.docker.com/)
3. Download Maven dependency manager: [Maven Download](https://maven.apache.org/download.cgi)

## Installation and Getting Started
The following repo contains examples for OpenFin's Java adapter.

1. Clone this repository

```shell
	git clone https://github.com/juliozarate5/lhh-exercise-2.git
```

2. Enter the application directory

```shell
	cd ./lhh-exercise-2
```

3. Clean & Build project.

```shell
	mvn clean install
```

4. Deployment on Docker :rocket:
```shell
	docker compose up -d
```

5. Unit tests:
```shell
	mvn test
```
You can detail coverage report in \{LAYER}\target\site and open index.html on browser; JaCoCo plugin is used for it.

### Note (Optional):
You can configure properties how: port & others, in: \src\main\resources\application.yml

## Source Code Review

Source code for the example is located in:

\src\main\java\com\lhh\ms\token

1. Run Application from Main class in \src\main\java\com\lhh\ms\token:

```java
@EnableFeignClients
@SpringBootApplication
public class MsTokenApplication {

    @Autowired
    TokenClientHttp tokenClientHttp;

    public static void main(String[] args) {
        SpringApplication.run(MsTokenApplication.class, args);

    }

}

```
This code just creates an instance of DesktopConnection and it does not try to connect to runtime.
@EnableFeignClients enables Client to consume external service.

2. TokenController  contains the method for retrieve token:

```java
    @PostMapping
public ResponseEntity<TokenResponse> getToken(@RequestBody @Validated TokenRequest tokenRequest) {
        log.info("Executing getToken from Controller...");
final TokenRequestDTO tokenRequestDTO = tokenMapper.toTokenRequestDTO(tokenRequest);
        return ResponseEntity.ok(
        tokenMapper.toTokenResponse(tokenService.getToken(tokenRequestDTO))
        );
        }
```

3. H2TokenRepository is Adapter Repository:

 ```java
    @Override
public Optional<Token> findTokenByUsername(String username) {
final Optional<TokenEntity> tokenEntityOptional = tokenJpaRepository.findByUsername(username);
        return tokenMapper.toPriceOptional(tokenEntityOptional);
        }

@Override
public Token saveToken(Token token) {
final TokenEntity tokenEntity = tokenJpaRepository.save(tokenMapper.toTokenEntity(token));
        return tokenMapper.toToken(tokenEntity);
        }
```
This methods are Implementing of Domain repository for retrieve token with params & save

4.  In infrastructure layer is Entities how TokenEntity:

```java
@Entity
@Table(name = "tokens")
@FieldDefaults(level = PRIVATE)
@Getter
@Setter
public class TokenEntity implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(unique = true)
    String token;

    @Column
    LocalDateTime timestamp;

    @Column
    LocalDateTime expiration;

    @Column(unique = true)
    String username;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if(timestamp == null) {
            timestamp = LocalDateTime.now();
        }
        if(expiration == null) {
            expiration = timestamp.plusSeconds(60);
        }

    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
        if(timestamp != null) {
            timestamp = LocalDateTime.now();
            if(expiration != null) {
                expiration = timestamp.plusSeconds(60);
            }
        }

    }
}

```

5. It's use Mapstruct for Mapping classes:

```java
@Mapper
public interface TokenMapper {

    TokenMapper INSTANCE = Mappers.getMapper(TokenMapper.class);

    default TokenResponse toTokenResponse(TokenResponseDTO tokenResponseDTO) {
        final LocalDateTime timestamp = tokenResponseDTO.getTimestamp();
        final ZonedDateTime zonedDateTime =  ZonedDateTime.of(timestamp, ZoneId.of("Europe/Madrid"));
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        final String isoTimestamp = zonedDateTime.format(formatter);
        return TokenResponse.builder()
                .token(tokenResponseDTO.getToken())
                .timestamp(isoTimestamp)
                .build();
    }
...

```

6. In infrastructure\config, is configurations: beans, swagger & interceptor for exceptions:

```java
@Configuration
public class TokenConfiguration {

    TokenService tokenService(final TokenRepository tokenRepository) {
        return new DomainTokenServiceImpl(tokenRepository);
    }
}
```
Communicates with application layer

7. In Application Layer is the TokenService

```java
@Transactional
@Override
public TokenResponseDTO getToken(TokenRequestDTO tokenRequest) {
        Optional<Token> existingToken = tokenRepository.findTokenByUsername(tokenRequest.getUsername());

        if (existingToken.isPresent() && isTokenNotExpired(existingToken.get())) {
        return tokenAppMapper.toTokenResponseDTO(existingToken.get());
        }

        return getAndUpdateToken(tokenRequest, existingToken);
        }

```

6. ..\domain layer contains: DTOs, exceptions classes, models & Ports for infraestructura layer connects to the model

```java
/**
 * Port: for infraestructura layer connects to the model
 */
public interface TokenRepository {

    Optional<Token> findTokenByUsername(String username);

    Token saveToken(Token token);
}

```

This method contains the bussines logic for get token & save or update.

## Operations

Test API on Swagger UI: http://localhost:{port}/api/v1/swagger-ui.html

Note: default port in this app is 8081. You can configure in application.yml & relaunch app.

### Get Token by Username & Password

`POST` `/token`

Body 

`{
"username":"auth-vivelibre",
"password":"password"
}`

This feature retrieves the token with timestamp data 
from the database if token is in database and this is not expired
or external Microservice Token provider.

### Response 200 OK

```json
{
  "token": "string",
  "timestamp": "string"
}
```

### Response 400 Bad Request

```json
{
  "type": "string",
  "title": "string",
  "status": 0,
  "detail": "string",
  "instance": "string",
  "properties": {
    "additionalProp1": {},
    "additionalProp2": {},
    "additionalProp3": {}
  }
}
```

### Response 401 Unauthorized

```json
{
  "error": "string",
  "message": "string",
  "status": 0,
  "date": "2025-03-03T06:41:51.096Z"
}
```

### Response 500 Internal Error Server

```json
{
  "error": "string",
  "message": "string",
  "status": 500,
  "date": "2025-01-21T01:06:26.761Z"
}
```

## Use Project

1. Download & setup IntelliJ Community Edition(Recommended) : [IntelliJ Download](https://www.jetbrains.com/es-es/idea/download/?section=windows)
2. Open project: File -> Open
3. Run from: MsTokenApplication main class or execute command:
```shell
	mvn spring-boot:run
```
4. For unit testing, execute command:
```shell
	mvn test
```

## More Info
More information and API documentation can be found at https://www.lhh.com/

## Disclaimers
* This is a starter example and intended to demonstrate to app providers a sample of how to approach an implementation. There are potentially other ways to approach it and alternatives could be considered.
* Its possible that the repo is not actively maintained.

## License
LHH Recruitment Solutions

https://www.lhh.com/es/es/contacta-con-nosotros/

## Support
Please enter an issue in the repo for any questions or problems.
<br> Alternatively, please contact us at juliomzarate5@gmail.com