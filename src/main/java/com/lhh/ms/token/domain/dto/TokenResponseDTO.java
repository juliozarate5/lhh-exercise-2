package com.lhh.ms.token.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDTO {

    Long id;

    String token;

    LocalDateTime timestamp;
}
