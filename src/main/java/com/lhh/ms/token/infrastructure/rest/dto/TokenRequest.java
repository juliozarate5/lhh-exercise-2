package com.lhh.ms.token.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {

    @NotNull(message = "username required")
    String username;

    @NotNull(message = "password required")
    String password;
}
