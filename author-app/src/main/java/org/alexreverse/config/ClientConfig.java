package org.alexreverse.config;

import org.alexreverse.client.WebClientFavouritePostsClient;
import org.alexreverse.client.WebClientPostReviewsClient;
import org.alexreverse.client.WebClientPostsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {


    @Bean
    @Scope("prototype")
    public WebClient.Builder doomerhubServicesWebClientBuilder(
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ServerOAuth2AuthorizedClientRepository authorizedClientRepository
    ) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction filter =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository,
                        authorizedClientRepository);
        filter.setDefaultClientRegistrationId("keycloak");
        return WebClient.builder()
                .filter(filter);
    }
    @Bean
    public WebClientPostsClient webClientPostsClient(
            @Value("${doomerhub.services.search.uri:http://localhost:8083}") String baseUrl,
            WebClient.Builder doomerhubServicesWebClientBuilder
    ) {
        return new WebClientPostsClient(doomerhubServicesWebClientBuilder
                .baseUrl(baseUrl)
                .build());
    }

    @Bean
    public WebClientFavouritePostsClient webClientFavouritePostsClient(
            @Value("${doomerhub.services.search.uri:http://localhost:8083}") String feedbackBaseUrl,
            WebClient.Builder doomerhubServicesWebClientBuilder
    ) {
        return new WebClientFavouritePostsClient(doomerhubServicesWebClientBuilder
                .baseUrl(feedbackBaseUrl)
                .build());
    }

    @Bean
    public WebClientPostReviewsClient webClientPostReviewsClient(
            @Value("${doomerhub.services.search.uri:http://localhost:8083}") String feedbackBaseUrl,
            WebClient.Builder doomerhubServicesWebClientBuilder
    ) {
        return new WebClientPostReviewsClient(doomerhubServicesWebClientBuilder
                .baseUrl(feedbackBaseUrl)
                .build());
    }
}
