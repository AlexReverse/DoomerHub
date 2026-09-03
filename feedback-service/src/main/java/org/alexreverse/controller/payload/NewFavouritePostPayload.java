package org.alexreverse.controller.payload;

import jakarta.validation.constraints.NotNull;

public record NewFavouritePostPayload(@NotNull(message = "feedback.posts.favourites.create.errors.post_id_is_null")
                                      Long postId,
                                      @NotNull(message = "feedback.posts.favourites.create.errors.user_id_is_null")
                                      String userName) {
}
