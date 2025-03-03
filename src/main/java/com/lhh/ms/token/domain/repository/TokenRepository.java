package com.lhh.ms.token.domain.repository;

import com.lhh.ms.token.domain.model.Token;

import java.util.Optional;

/**
 * Port: for infraestructura layer connects to the model
 */
public interface TokenRepository {

    Optional<Token> findTokenByUsername(String username);

    Token saveToken(Token token);
}
