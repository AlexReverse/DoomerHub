package org.alexreverse.config;

import org.alexreverse.client.WebClientPostsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Bean
    public WebClientPostsClient webClientPostsClient(
            @Value("${doomerhub.services.search.uri:http://localhost:8081}") String baseUrl
    ) {
        return new WebClientPostsClient(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }
}
