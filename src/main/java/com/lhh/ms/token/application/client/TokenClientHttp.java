package com.lhh.ms.token.application.client;

import com.lhh.ms.token.domain.dto.TokenClientDTO;
import com.lhh.ms.token.domain.dto.TokenRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "token-service", url = "http://localhost:8080")
public interface TokenClientHttp {

    @PostMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    TokenClientDTO getToken(@RequestBody TokenRequestDTO tokenRequest);
}
