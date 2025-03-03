package com.lhh.ms.token.infrastructure.rest;

import com.lhh.ms.token.application.service.TokenService;
import com.lhh.ms.token.domain.dto.TokenRequestDTO;
import com.lhh.ms.token.infrastructure.mapper.TokenMapper;
import com.lhh.ms.token.infrastructure.rest.dto.ErrorDTO;
import com.lhh.ms.token.infrastructure.rest.dto.TokenRequest;
import com.lhh.ms.token.infrastructure.rest.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Endpoints Token Controller", description = "Token Controller")
@RequestMapping("/token")
@RestController
@Slf4j
public class TokenController {

    private static final TokenMapper tokenMapper = TokenMapper.INSTANCE;

    private final TokenService tokenService;


    public TokenController(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "500", description = "Internal Error Server", content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
                    ),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content =
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
                    )
            }
    )
    @Operation(
            summary = "Get Token by Username & Password",
            description = "Get Token from external Microservice Token provider"
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseEntity<TokenResponse> getToken(@RequestBody @Validated TokenRequest tokenRequest) {
        log.info("Executing getToken from Controller...");
        final TokenRequestDTO tokenRequestDTO = tokenMapper.toTokenRequestDTO(tokenRequest);
        return ResponseEntity.ok(
                tokenMapper.toTokenResponse(tokenService.getToken(tokenRequestDTO))
        );
    }
}
