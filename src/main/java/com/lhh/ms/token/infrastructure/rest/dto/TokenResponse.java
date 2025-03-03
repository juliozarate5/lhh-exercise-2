package com.lhh.ms.token.infrastructure.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse implements Serializable {

    String token;

    String timestamp;

}
