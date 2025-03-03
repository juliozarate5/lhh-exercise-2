package com.lhh.ms.token.application.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhh.ms.token.application.client.TokenClientHttp;
import com.lhh.ms.token.application.exceptions.InternalServerErrorException;
import com.lhh.ms.token.application.exceptions.InvalidTokenException;
import com.lhh.ms.token.application.mapper.TokenAppMapper;
import com.lhh.ms.token.application.service.TokenService;
import com.lhh.ms.token.domain.dto.TokenClientDTO;
import com.lhh.ms.token.domain.dto.TokenRequestDTO;
import com.lhh.ms.token.domain.dto.TokenResponseDTO;
import com.lhh.ms.token.domain.model.Token;
import com.lhh.ms.token.domain.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Optional;

/**
 * Adapter : Logic
 */
@Service
@Slf4j
public class DomainTokenServiceImpl implements TokenService {

    @Value("${tokens.expiration}")
    private Long expirationSeconds;

    private static final TokenAppMapper tokenAppMapper = TokenAppMapper.INSTANCE;

    private final TokenRepository tokenRepository;

    @Autowired
    private TokenClientHttp tokenClientHttp;

    @Autowired
    public DomainTokenServiceImpl(final TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    @Override
    public TokenResponseDTO getToken(TokenRequestDTO tokenRequest) {
        Optional<Token> existingToken = tokenRepository.findTokenByUsername(tokenRequest.getUsername());

        if (existingToken.isPresent() && isTokenNotExpired(existingToken.get())) {
            return tokenAppMapper.toTokenResponseDTO(existingToken.get());
        }

        return getAndUpdateToken(tokenRequest, existingToken);
    }

    private boolean isTokenNotExpired(Token token) {
        return LocalDateTime.now().isBefore(token.getExpiration()) && LocalDateTime.now().isBefore(token.getExpirationToken());
    }

    private TokenResponseDTO getAndUpdateToken(TokenRequestDTO tokenRequest, Optional<Token> existingToken) {
        try{
            TokenClientDTO tokenClientDTO = tokenClientHttp.getToken(tokenRequest);
            if (!isValidToken(tokenClientDTO.getToken())) {
                throw new InvalidTokenException("Token inválido o vacío");
            }
            Token token;
            if (existingToken.isPresent()) {
                // Update actual token
                token = existingToken.get();
                token.setToken(tokenClientDTO.getToken());
                token.setExpiration(calculateExpirationTime());
                token.setUpdatedAt(LocalDateTime.now());
                token.setExpirationToken(getDateExpirationToken(tokenClientDTO.getToken()));
            } else {
                // Create new Token
                token = createNewToken(tokenClientDTO, tokenRequest.getUsername());
            }
            tokenRepository.saveToken(token);
            return tokenAppMapper.toTokenResponseDTO(token);
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid credentials");
        }
    }

    private Token createNewToken(TokenClientDTO tokenClientDTO, String username) {
        Token token = tokenAppMapper.toToken(tokenAppMapper.toTokenEntity(tokenClientDTO, username));
        token.setExpiration(calculateExpirationTime());
        token.setExpirationToken(getDateExpirationToken(tokenClientDTO.getToken()));
        token.setUpdatedAt(LocalDateTime.now());
        return token;
    }

    private boolean isValidToken(String token) {
        return StringUtils.isNotEmpty(token);
    }

    private LocalDateTime calculateExpirationTime() {
        return LocalDateTime.now().plusSeconds(expirationSeconds);// expiracion personalizada
    }

    private LocalDateTime getDateExpirationToken(String token) {
        final long epochSeconds;
        try {
            epochSeconds = getExpirationFromToken(token);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return LocalDateTime.ofInstant(
                Instant.ofEpochSecond(epochSeconds),
                ZoneId.systemDefault()
        );
    }

    private static long getExpirationFromToken(String token) throws Exception {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new InvalidTokenException("Token inválido o vacío");
        }
        String payload = parts[1];
        byte[] decodedPayload = Base64.getUrlDecoder().decode(payload);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode payloadJson = objectMapper.readTree(decodedPayload);
        return payloadJson.get("exp").asLong();
    }
}
