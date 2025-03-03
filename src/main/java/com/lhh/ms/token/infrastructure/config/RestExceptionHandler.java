package com.lhh.ms.token.infrastructure.config;

import com.lhh.ms.token.application.exceptions.InternalServerErrorException;
import com.lhh.ms.token.application.exceptions.InvalidTokenException;
import com.lhh.ms.token.infrastructure.rest.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice(basePackages = "com.lhh.ms.token.infrastructure.rest")
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorDTO> getGeneralException(Exception e) {
        log.error(e.getMessage(), e);
        ErrorDTO errorRq = ErrorDTO.getErrorDto(
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorRq, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({InternalServerErrorException.class})
    public ResponseEntity<ErrorDTO> getGeneralException(InternalServerErrorException e) {
        log.error(e.getMessage(), e);
        return getErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<ErrorDTO> getNotFoundRquest(InvalidTokenException e) {
        log.info(e.getMessage());
        return getErrorDTO(HttpStatus.UNAUTHORIZED, e);
    }

    private ResponseEntity<ErrorDTO> getErrorDTO(HttpStatus httpStatus, Exception e) {
        final ErrorDTO errorDTO = ErrorDTO.builder()
                .error(httpStatus.getReasonPhrase())
                .status(httpStatus.value())
                .message(e.getMessage())
                .date(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorDTO, httpStatus);
    }
}