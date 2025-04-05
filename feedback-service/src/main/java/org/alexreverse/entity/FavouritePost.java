package org.alexreverse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Column(name = "user")
    @NotNull
    @Size(max = 50)
    private String user;
}
