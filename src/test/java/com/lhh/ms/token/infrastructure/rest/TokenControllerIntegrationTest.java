package com.lhh.ms.token.infrastructure.rest;

import com.lhh.ms.token.application.exceptions.InvalidTokenException;
import com.lhh.ms.token.infrastructure.rest.dto.TokenRequest;
import com.lhh.ms.token.infrastructure.rest.dto.TokenResponse;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TokenControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @DisplayName("Test 1 get token by body successful")
    @Test
    void get_token_shouldGetSuccessful_test_1() {
        // Given
        HttpStatus expected = HttpStatus.OK;
        // When
        TokenRequest tokenRequest = TokenRequest.builder()
                .username("auth-vivelibre")
                .password("password")
                .build();


        ResponseEntity<TokenResponse> actual = testRestTemplate.postForEntity(
                "http://localhost:"+port+"/api/v1/token",
                tokenRequest,
                TokenResponse.class
        );

        // Then
        BDDAssertions.then(actual.getStatusCode()).isEqualTo(expected);
        BDDAssertions.then(actual.getBody()).isNotNull();
        BDDAssertions.then(actual.getBody().getToken()).isNotEmpty();
        BDDAssertions.then(actual.getBody().getToken()).contains("ey");
        BDDAssertions.then(actual.getBody().getToken()).isGreaterThan("10");

    }

    @DisplayName("Test 1 get token by body error: username not present")
    @Test
    void get_token_shouldGetError_username_not_present() {
        // Given
        HttpStatus expected = HttpStatus.BAD_REQUEST;
        // When
        TokenRequest tokenRequest = TokenRequest.builder()
                .password("password")
                .build();


        ResponseEntity<TokenResponse> actual = testRestTemplate.postForEntity(
                "http://localhost:"+port+"/api/v1/token",
                tokenRequest,
                TokenResponse.class
        );

        // Then
        BDDAssertions.then(actual.getStatusCode()).isEqualTo(expected);
    }
}
