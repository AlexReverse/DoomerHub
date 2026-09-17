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

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    Post payloadToEntity(NewPostPayload payload);

    PostDto entityToDto(Post entity);

    void updateEntity(UpdatePostPayload payload, @MappingTarget Post entity);

    PostReview payloadToEntity(NewPostReviewPayload payload);

    PostReviewDto entityToDto(PostReview entity);

    FavouritePost payloadToEntity(NewFavouritePostPayload payload);

    FavouritePostDto entityToDto(FavouritePost entity);
}
