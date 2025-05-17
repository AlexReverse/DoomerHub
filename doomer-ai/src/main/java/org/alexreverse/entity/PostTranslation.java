package org.alexreverse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "aitranslation", name = "english_translation")
public class PostTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id")
    @NotNull
    private Long postId;

    @Column(name = "translated_description")
    @NotNull
    @Size(max = 5000)
    private String translatedDescription;

    @Column(name = "translated_date")
    @NotNull
    private LocalDateTime translatedDate;

}
