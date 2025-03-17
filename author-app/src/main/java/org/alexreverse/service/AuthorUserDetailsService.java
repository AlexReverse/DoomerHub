//package org.alexreverse.service;
//
//import lombok.RequiredArgsConstructor;
//import org.alexreverse.entity.AuthorUser;
//import org.alexreverse.repository.AuthorUserRepository;
//import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//@Service
//@RequiredArgsConstructor
//public class AuthorUserDetailsService implements ReactiveUserDetailsService, ReactiveClientRegistrationRepository {
//
//    private final AuthorUserRepository userRepository;
//
//    @Override
//    public Mono<UserDetails> findByUsername(String username) {
//        Mono<AuthorUser> data = userRepository.findByUsername(username);
//        return data.cast(UserDetails.class);
//    }
//
//    @Override
//    public Mono<ClientRegistration> findByRegistrationId(String registrationId) {
//        return null;
//    }
//}
