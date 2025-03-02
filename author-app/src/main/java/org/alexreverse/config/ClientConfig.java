package org.alexreverse.config;

import org.alexreverse.client.WebClientFavouritePostsClient;
import org.alexreverse.client.WebClientPostReviewsClient;
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

    @Bean
    public WebClientFavouritePostsClient webClientFavouritePostsClient(
            @Value("${doomerhub.services.feedback.uri:http://localhost:8083}") String feedbackBaseUrl
    ) {
        return new WebClientFavouritePostsClient(WebClient.builder()
                .baseUrl(feedbackBaseUrl)
                .build());
    }

    @Bean
    public WebClientPostReviewsClient webClientPostReviewsClient(
            @Value("${doomerhub.services.feedback.uri:http://localhost:8083}") String feedbackBaseUrl
    ) {
        return new WebClientPostReviewsClient(WebClient.builder()
                .baseUrl(feedbackBaseUrl)
                .build());
    }
}
