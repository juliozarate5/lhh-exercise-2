package com.lhh.ms.token.application.service.impl;

import com.lhh.ms.token.application.client.TokenClientHttp;
import com.lhh.ms.token.domain.dto.TokenClientDTO;
import com.lhh.ms.token.domain.dto.TokenRequestDTO;
import com.lhh.ms.token.domain.dto.TokenResponseDTO;
import com.lhh.ms.token.domain.model.Token;
import com.lhh.ms.token.domain.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DomainTokenServiceImplTest {

    @InjectMocks
    private DomainTokenServiceImpl domainTokenService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TokenClientHttp tokenClientHttp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Test getToken successful")
    @Test
    void getToken() {
        ReflectionTestUtils.setField(domainTokenService, "expirationSeconds", 3600L);
        // Arrange
        TokenClientDTO tokenClientDTO = new TokenClientDTO();
        tokenClientDTO.setToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhdXRoLXZpdmVsaWJyZSIsImV4cCI6MTc0MTAwNDcyMCwiaWF0IjoxNzQwOTg2NzIwfQ.vZ-RFcyhiXTdqBI-tCHtH0o4Addo93RwpqvIS0UYg-A4UyhF3CeT2nqfqMLTtt_0cnvOV3YJuGoNVoTv7TrfIA");

      Mockito.when(tokenClientHttp.getToken(
                ArgumentMatchers.any()
        )).thenReturn(tokenClientDTO);
        // Act
        final TokenResponseDTO result = domainTokenService.getToken(TokenRequestDTO.builder().build());

        // Assert
        assertNotNull(result);
        assertEquals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhdXRoLXZpdmVsaWJyZSIsImV4cCI6MTc0MTAwNDcyMCwiaWF0IjoxNzQwOTg2NzIwfQ.vZ-RFcyhiXTdqBI-tCHtH0o4Addo93RwpqvIS0UYg-A4UyhF3CeT2nqfqMLTtt_0cnvOV3YJuGoNVoTv7TrfIA", result.getToken());

       Mockito.verify(tokenClientHttp, Mockito.times(1)).getToken(ArgumentMatchers.any());
    }
}