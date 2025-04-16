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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "doomerhub",name = "favourite_post")
public class FavouritePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(name = "post_id")
    @NotNull
    private Long postId;

    @Column(name = "user_name")
    @NotNull
    @Size(max = 50)
    private String userName;

    @Column(name = "favourite_post_date")
    @NotNull
    private LocalDateTime favouritePostDate;

}
