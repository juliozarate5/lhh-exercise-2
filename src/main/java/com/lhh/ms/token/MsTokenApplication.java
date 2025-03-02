package com.lhh.ms.token;

import com.lhh.ms.token.infrastructure.client.TokenClientHttp;
import com.lhh.ms.token.infrastructure.client.dto.TokenRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsTokenApplication implements CommandLineRunner {

	@Autowired
	TokenClientHttp tokenClientHttp;

	public static void main(String[] args) {
		SpringApplication.run(MsTokenApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		// TODO: PONERLO EN UNIT TESTING
		/*TokenRequestDTO tokenRequestDTO = TokenRequestDTO.builder()
				.username("auth-vivelibre")
				.password("password")
				.build();
		System.out.println(tokenClientHttp.getToken(tokenRequestDTO).toString());*/
	}
}
