package com.lhh.ms.token.infrastructure.repository;

import com.lhh.ms.token.domain.model.Token;
import com.lhh.ms.token.domain.repository.TokenRepository;
import com.lhh.ms.token.infrastructure.mapper.TokenMapper;
import com.lhh.ms.token.infrastructure.persistence.entity.TokenEntity;
import com.lhh.ms.token.infrastructure.persistence.repository.TokenJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class H2TokenRepository implements TokenRepository {

    private static final TokenMapper tokenMapper = TokenMapper.INSTANCE;

    private final TokenJpaRepository tokenJpaRepository;

    @Autowired
    public H2TokenRepository(final TokenJpaRepository tokenJpaRepository) {
        this.tokenJpaRepository = tokenJpaRepository;
    }

    @Override
    public Optional<Token> findTokenByUsername(String username) {
        final Optional<TokenEntity> tokenEntityOptional = tokenJpaRepository.findByUsername(username);
        return tokenMapper.toPriceOptional(tokenEntityOptional);
    }

    @Override
    public Token saveToken(Token token) {
        final TokenEntity tokenEntity = tokenJpaRepository.save(tokenMapper.toTokenEntity(token));
        return tokenMapper.toToken(tokenEntity);
    }
}
