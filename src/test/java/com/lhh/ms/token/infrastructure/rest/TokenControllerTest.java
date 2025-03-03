package com.lhh.ms.token.infrastructure.rest;

import com.lhh.ms.token.application.service.TokenService;
import com.lhh.ms.token.domain.dto.TokenResponseDTO;
import com.lhh.ms.token.infrastructure.rest.dto.TokenRequest;
import com.lhh.ms.token.infrastructure.rest.dto.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class TokenControllerTest {

    @InjectMocks
    private TokenController tokenController;

    @Mock
    private TokenService tokenService;

    @DisplayName("Test get token by body")
    @Test
    void get_prices_by_params_successful() {

        Mockito.when(
                tokenService.getToken( ArgumentMatchers.any() )
        ).thenReturn(TokenResponseDTO.builder()
                        .id(1L)
                        .timestamp(LocalDateTime.of(2020, 6, 14, 10, 0, 0))
                        .token("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhdXRoLXZpdmVsaWJyZSIsImV4cCI6MTc0MTAwNDcyMCwiaWF0IjoxNzQwOTg2NzIwfQ.vZ-RFcyhiXTdqBI-tCHtH0o4Addo93RwpqvIS0UYg-A4UyhF3CeT2nqfqMLTtt_0cnvOV3YJuGoNVoTv7TrfIA")
                .build());
        final ResponseEntity<TokenResponse> response =
                tokenController.getToken(
                        TokenRequest.builder()
                                .username("username")
                                .password("password")
                                .build()
                );
        assertNotNull(response);
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
        final TokenResponse item = response.getBody();
        assertNotNull(item);
    }
}