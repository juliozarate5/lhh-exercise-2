package com.lhh.ms.token.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@Builder
@AllArgsConstructor
public class TokenRequestDTO {

    String username;

    String password;
}
