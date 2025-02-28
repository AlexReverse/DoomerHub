package org.alexreverse.repository;

import org.alexreverse.entity.FavouritePost;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Repository
public class InMemoryFavouritePostRepository implements FavouritePostRepository {

    private final List<FavouritePost> favouritePostList = Collections.synchronizedList(new LinkedList<>());
    @Override
    public Mono<FavouritePost> save(FavouritePost favouritePost) {
        this.favouritePostList.add(favouritePost);
        return Mono.just(favouritePost);
    }

    @Override
    public Mono<Void> deleteByPostId(Integer postId) {
        this.favouritePostList.removeIf(favouritePost -> favouritePost.getPostId().equals(postId));
        return Mono.empty();
    }

    @Override
    public Mono<FavouritePost> findByPostId(Integer postId) {
        return Flux.fromIterable(this.favouritePostList)
                .filter(favouritePost -> favouritePost.getPostId().equals(postId))
                .singleOrEmpty();
    }

    @Override
    public Flux<FavouritePost> findAll() {
        return Flux.fromIterable(this.favouritePostList);
    }
}
