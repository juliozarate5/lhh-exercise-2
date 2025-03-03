package com.lhh.ms.token.application.service;

import com.lhh.ms.token.domain.dto.TokenRequestDTO;
import com.lhh.ms.token.domain.dto.TokenResponseDTO;

public interface TokenService {

    TokenResponseDTO getToken(TokenRequestDTO tokenRequest);
}
