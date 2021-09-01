package com.example.njunji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class NjunjiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NjunjiApplication.class, args);
	}

	@Bean
	public WebClient webClient(WebClient.Builder builder){
		return builder.baseUrl("https://beep2.cellulant.africa:9001")
				.defaultHeader("Content-Type","application/json")
				.build();
	}

}
