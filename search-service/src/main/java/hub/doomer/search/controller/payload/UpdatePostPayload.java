package hub.doomer.search.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdatePostPayload(
        @NotNull(message = "{search.posts.update.errors.title_is_null}")
        @Size(min = 3, max = 50, message = "{search.posts.update.errors.title_size_is_invalid}")
        String title,
        @Size(min = 100, max = 5000, message = "{search.posts.update.errors.description_size_is_invalid}")
        String description) {}
