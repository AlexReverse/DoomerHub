package org.alexreverse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("post_review")
public class PostReview {

    @Id
    private UUID id;
    private int postId;
    private int rating;
    private String review;
}
