package org.alexreverse.service;

import org.alexreverse.dto.PostGetTranslate;
import org.alexreverse.dto.PostReturnTranslate;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ChatClient client;

    public AiService(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    public PostReturnTranslate getTranslateToEnglish(PostGetTranslate post) {
        ChatClient.CallResponseSpec callResponseSpec = client.prompt().user("Переведи текст на Английский: " +
                post.text()).call();
        String content = callResponseSpec.content();
        return new PostReturnTranslate(content);
    }
}
