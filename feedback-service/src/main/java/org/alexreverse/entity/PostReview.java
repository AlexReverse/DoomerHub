package org.alexreverse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "doomerhub",name = "post_review")
public class PostReview {

    @Id
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(schema = "doomerhub", name = "post_review_list",
            joinColumns = @JoinColumn(name = "id_favourite_post"),
            inverseJoinColumns = @JoinColumn(name = "id_post"))
    private Long postId;
    private Long rating;
    private String review;
}
