package com.lhh.ms.token.infrastructure.mapper;

import com.lhh.ms.token.domain.dto.TokenRequestDTO;
import com.lhh.ms.token.domain.dto.TokenResponseDTO;
import com.lhh.ms.token.domain.model.Token;
import com.lhh.ms.token.infrastructure.persistence.entity.TokenEntity;
import com.lhh.ms.token.infrastructure.rest.dto.TokenRequest;
import com.lhh.ms.token.infrastructure.rest.dto.TokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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

    TokenRequestDTO toTokenRequestDTO(TokenRequest tokenRequest);

    Token toToken(TokenEntity tokenEntity);

    TokenEntity toTokenEntity(Token token);

    default Optional<Token> toPriceOptional(Optional<TokenEntity> priceEntityOptional) {
        return priceEntityOptional.map(this::toToken);
    }

}
