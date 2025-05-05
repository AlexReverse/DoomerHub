package org.alexreverse.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ChatClient client;

    public AiService(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    public String getTranslateToEnglish(String post) {
        ChatClient.CallResponseSpec callResponseSpec = client.prompt().user("Переведи текст на Английский: " +
                post).call();
        return callResponseSpec.content();
    }
}
