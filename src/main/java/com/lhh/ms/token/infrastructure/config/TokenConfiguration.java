package com.lhh.ms.token.infrastructure.config;

import com.lhh.ms.token.application.service.TokenService;
import com.lhh.ms.token.application.service.impl.DomainTokenServiceImpl;
import com.lhh.ms.token.domain.repository.TokenRepository;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfiguration {

    TokenService tokenService(final TokenRepository tokenRepository) {
        return new DomainTokenServiceImpl(tokenRepository);
    }
}
