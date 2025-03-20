package org.alexreverse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .authorizeExchange(customizer -> customizer.pathMatchers("/author_login",
                                "/images/favicon.png", "style.css").permitAll()
                        .anyExchange().authenticated())
                .oauth2Login(oAuth2LoginSpec -> oAuth2LoginSpec.loginPage("/author_login"))
                .oauth2Client(Customizer.withDefaults())
                .build();
    }
}
