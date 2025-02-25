package com.hub.doomer.controller;

import com.hub.doomer.client.BadRequestException;
import com.hub.doomer.client.PostsRestClient;
import com.hub.doomer.controller.payload.NewPostPayload;
import com.hub.doomer.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.ConcurrentModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Модульные тесты PostsController")
class PostsControllerTest {

    @Mock
    PostsRestClient postsRestClient;

    @InjectMocks
    PostsController postsController;

    @Test
    @DisplayName("createPost создаст новый пост и перенаправит на страницу поста")
    void createPost_RequestIsValid_ReturnsRedirectionToPostPage() {
        // given
        var payload = new NewPostPayload("Новый пост", "Описание поста");
        var model = new ConcurrentModel();
        var response = new MockHttpServletResponse();

        doReturn(new Post(1, "Новый пост", "Описание поста"))
                .when(this.postsRestClient)
                .createPost("Новый пост", "Описание поста");
        // when
        var result = this.postsController.createPost(payload, model, response);

        // then
        assertEquals("redirect:/search/posts/1", result);

        verify(this.postsRestClient).createPost("Новый пост", "Описание поста");
        verifyNoMoreInteractions(this.postsRestClient);
    }

    @Test
    @DisplayName("createPost вернет страницу с ошибками, если запрос невалиден")
    void createPost_RequestIsInvalid_ReturnsPostFormWithErrors() {
        // given
        var payload = new NewPostPayload(" ", null);
        var model = new ConcurrentModel();
        var response = new MockHttpServletResponse();

        doThrow(new BadRequestException(List.of("Ошибка 1", "Ошибка 2")))
                .when(this.postsRestClient)
                .createPost(" ", null);
        // when
        var result = this.postsController.createPost(payload, model, response);

        // then
        assertEquals("search/posts/new_post", result);
        assertEquals(payload, model.getAttribute("payload"));
        assertEquals(List.of("Ошибка 1", "Ошибка 2"), model.getAttribute("errors"));

        verify(this.postsRestClient).createPost(" ", null);
        verifyNoMoreInteractions(this.postsRestClient);
    }
}