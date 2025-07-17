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
@Table(schema = "doomerhub", name = "author_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotNull
    @Size(min = 3, max = 50)
    private String title;

    @Column(name = "description")
    @NotNull
    @Size(max = 5000)
    private String description;

    @Column(name = "user_id")
    @NotNull
    @Size(max = 50)
    private String userId;

    @Column(name = "post_date")
    @NotNull
    private LocalDateTime postDate;
}
