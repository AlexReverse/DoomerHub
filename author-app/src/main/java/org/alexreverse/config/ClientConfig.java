package org.alexreverse.config;

import org.alexreverse.client.WebClientFavouritePostsClient;
import org.alexreverse.client.WebClientPostReviewsClient;
import org.alexreverse.client.WebClientPostsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Bean
    @Scope(scopeName = "prototype")
    public WebClientPostsClient webClientPostsClient(
            @Value("${doomerhub.services.search.uri:http://localhost:8083}") String baseUrl,
            @Value("${doomerhub.services.search.username:}") String searchUsername,
            @Value("${doomerhub.services.search.password:}") String searchPassword
    ) {
        return new WebClientPostsClient(WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(headers -> headers.setBasicAuth(searchUsername, searchPassword))
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
