package com.hub.doomer.config;

import com.hub.doomer.client.RestClientPostsRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {
    @Bean
    public RestClientPostsRestClient postsRestClient(
            @Value("${doomerhub.services.search.uri:http://localhost:8081}") String searchBaseUri,
            @Value("${doomerhub.services.search.username:}") String searchUsername,
            @Value("${doomerhub.services.search.password:}") String searchPassword){
        return new RestClientPostsRestClient(RestClient.builder()
                .baseUrl(searchBaseUri)
                .requestInterceptor(new BasicAuthenticationInterceptor(searchUsername, searchPassword))
                .build());
    }
}
