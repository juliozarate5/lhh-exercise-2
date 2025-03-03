package com.lhh.ms.token.infrastructure.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
@Getter
@Setter
@Builder
public class ErrorDTO implements Serializable {

    static final long serialVersionUID = 1L;

    String error;

    String message;

    int status;

    LocalDateTime date;

    public static ErrorDTO getErrorDto(String error, String message, int status) {
        return ErrorDTO.builder()
                .error(error)
                .message(message)
                .status(status)
                .date(LocalDateTime.now())
                .build();
    }
}
