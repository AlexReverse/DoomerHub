package hub.doomer.search.controller;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class PostsRestControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("/sql/posts.sql")
    @WithMockUser(roles={"USER","ADMIN"})
    void findPosts_ReturnPostsList() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders.get("/search-api/posts")
                .param("filter", "пост");
        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {"id":1, "title": "Пост 1", "description": "Описание поста 1"},
                                    {"id":2, "title": "Пост 2", "description": "Описание поста 2"}
                                ]""")
                );
    }

    @Test
    @WithMockUser(roles={"USER","ADMIN"})
    void createPost_RequestIsValid_ReturnsNewPost() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders.post("/search-api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"title": "Test", "description": "Test description Test description Test description Test description Test description Test description"}
                        """);
        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string(HttpHeaders.LOCATION, "http://localhost/search-api/posts/1"),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "id": 1,
                                    "title": "Test",
                                    "description": "Test description Test description Test description Test description Test description Test description"
                                }""")
                );
    }

    @Test
    @WithMockUser(roles={"USER","ADMIN"})
    void createPost_RequestIsInvalid_ReturnsProblemDetail() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders.post("/search-api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"title": " ", "description": null}
                        """)
                .locale(Locale.of("ru", "RU"));
        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                {
                                    "errors": [
                                    "Название поста должно быть от 3 до 50 символов"
                                    ]
                                }""")
                );
    }
}