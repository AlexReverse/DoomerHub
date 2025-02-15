package com.hub.doomer.config;

import com.hub.doomer.client.PostsRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {
    @Bean
    public PostsRestClientImpl postsRestClient(@Value("${doomerhub.service.search.uri:http://localhost:8081}")
                                                   String catalogBaseUri){
        return new PostsRestClientImpl(RestClient.builder()
                .baseUrl(catalogBaseUri)
                .build());
    }
}
