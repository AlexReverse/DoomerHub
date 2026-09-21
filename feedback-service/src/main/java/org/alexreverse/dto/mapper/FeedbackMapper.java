package org.alexreverse.dto.mapper;

import org.alexreverse.controller.payload.NewFavouritePostPayload;
import org.alexreverse.controller.payload.NewPostPayload;
import org.alexreverse.controller.payload.NewPostReviewPayload;
import org.alexreverse.controller.payload.UpdatePostPayload;
import org.alexreverse.dto.FavouritePostDto;
import org.alexreverse.dto.PostDto;
import org.alexreverse.dto.PostReviewDto;
import org.alexreverse.entity.FavouritePost;
import org.alexreverse.entity.Post;
import org.alexreverse.entity.PostReview;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    PostDto entityToDto(Post entity);

    Post createEntity(NewPostPayload payload, UUID userId, LocalDateTime postDate);

    void updateEntity(UpdatePostPayload payload, @MappingTarget Post entity);

    PostReviewDto entityToDto(PostReview entity);

    PostReview createEntity(NewPostReviewPayload payload, LocalDateTime postReviewDate);

    FavouritePostDto entityToDto(FavouritePost entity);

    FavouritePost createEntity(NewFavouritePostPayload payload, LocalDateTime favouritePostDate);
}
