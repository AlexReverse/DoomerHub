package org.alexreverse.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class SecurityLoginPage {

    @GetMapping("/author_login")
    public Mono<String> getLoginPage(Model model) {
        return Mono.just("authorization/author_login");
    }
}
