package org.alexreverse.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewPostPayload(
    @NotNull(message = "{feedback.posts.create.errors.title_is_null}")
    @Size(min = 3, max = 50, message = "{feedback.posts.create.errors.title_size_is_invalid}")
    String title,
    @Size(min = 100, max = 5000, message = "{feedback.posts.create.errors.description_size_is_invalid}")
    String description,
    @NotNull(message = "{feedback.posts.create.errors.userid_is_null}")
    String userId,
    @NotNull
    String englishTranslation) {}
