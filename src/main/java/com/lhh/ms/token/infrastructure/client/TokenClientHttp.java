package com.lhh.ms.token.infrastructure.client;

import com.lhh.ms.token.infrastructure.client.dto.TokenRequestDTO;
import com.lhh.ms.token.infrastructure.client.dto.TokenResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "token-service", url = "http://localhost:8080")
public interface TokenClientHttp {

    @PostMapping("/token")
    TokenResponseDTO getToken(@RequestBody TokenRequestDTO tokenRequestDTO);
}
