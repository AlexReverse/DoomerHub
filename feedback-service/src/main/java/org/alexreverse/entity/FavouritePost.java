package org.alexreverse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "doomerhub",name = "favourite_post")
public class FavouritePost {

    @Id
    private UUID idFavouritePost;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "doomerhub", name = "favourite_post_list",
            joinColumns = @JoinColumn(name = "id_favourite_post"),
            inverseJoinColumns = @JoinColumn(name = "id_post"))
    private int postId;
}
