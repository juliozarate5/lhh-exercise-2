package com.lhh.ms.token.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor
public class TokenClientDTO {

    String token;
}
