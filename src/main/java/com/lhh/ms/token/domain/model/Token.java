package com.lhh.ms.token.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@Builder
public class Token {

    Long id;

    String token;

    LocalDateTime timestamp;

    LocalDateTime expiration; // expiration in this Microservice

    LocalDateTime expirationToken; // expiration real JWT

    String username;

    LocalDateTime updatedAt;
}
