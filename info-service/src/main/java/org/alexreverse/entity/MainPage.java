package org.alexreverse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "doomerhub", name = "main_page")
public class MainPage implements Persistable<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "nickname")
    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;

    @Column(name = "name")
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @Column(name = "sur_name")
    @NotNull
    @Size(min = 3, max = 50)
    private String surName;

    @Column(name = "city")
    @NotNull
    @Size(min = 3, max = 50)
    private String city;

    @Column(name = "birth_day")
    @NotNull
    @Size(min = 14, max = 100)
    private LocalDate birthDay; //year, month, day

    @Column(name = "description")
    @Size(max = 100)
    private String description;

    @Column(name = "registration_date")
    @NotNull
    private LocalDateTime registrationDate;

    @Transient
    private boolean isNew;

    @Override
    public UUID getId() {
        return userId;
    }

    @Override
    @Transient
    public boolean isNew() {
        return isNew;
    }
}
