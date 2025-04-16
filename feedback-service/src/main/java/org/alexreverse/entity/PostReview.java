package org.alexreverse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "doomerhub",name = "post_review")
public class PostReview {

    @Id
    private Long id;

    @ManyToOne
    @Column(name = "post_id")
    @NotNull
    private Long postId;

    @Column(name = "review")
    @NotNull
    @Size(min = 3, max = 100)
    private String review;

    @Column(name = "user_name")
    @NotNull
    private String userName;

    @Column(name = "post_review_date")
    @NotNull
    private LocalDateTime postReviewDate;
}
