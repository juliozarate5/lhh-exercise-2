package com.lhh.ms.token.application.mapper;

import com.lhh.ms.token.domain.dto.TokenClientDTO;
import com.lhh.ms.token.domain.dto.TokenResponseDTO;
import com.lhh.ms.token.domain.model.Token;
import com.lhh.ms.token.infrastructure.persistence.entity.TokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper
public interface TokenAppMapper {

    TokenAppMapper INSTANCE = Mappers.getMapper(TokenAppMapper.class);


    default TokenEntity toTokenEntity(TokenClientDTO tokenClientDTO, String username) {
        final LocalDateTime timestamp = LocalDateTime.now();
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(tokenClientDTO.getToken());
        tokenEntity.setTimestamp(timestamp);
        tokenEntity.setUsername(username);
        return tokenEntity;
    }

    Token toToken(TokenEntity tokenEntity);

    TokenResponseDTO toTokenResponseDTO(Token token);
}
