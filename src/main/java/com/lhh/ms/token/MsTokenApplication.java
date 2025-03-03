package com.lhh.ms.token;

import com.lhh.ms.token.application.client.TokenClientHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsTokenApplication {

	@Autowired
	TokenClientHttp tokenClientHttp;

	public static void main(String[] args) {
		SpringApplication.run(MsTokenApplication.class, args);

	}

}
