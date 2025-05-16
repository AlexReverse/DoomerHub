package org.alexreverse.controller.payload;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record NewPostTranslation(
        @NotNull
        Long postId,
        @NotNull
        @Size(min = 100, max = 5000)
        String description) {}
