package org.alexreverse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReview {

    private UUID id;
    private Integer postId;
    private Integer rating;
    private String review;
}
