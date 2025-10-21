package org.alexreverse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "doomerhub", name = "main_page")
public class MainPage {

    @Id
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

    @Column(name = "age")
    @NotNull
    @Size(min = 14, max = 100)
    private Date birthDay; //year, month, day

    @Column(name = "description")
    @Size(max = 100)
    private String description;

    @Column(name = "registration_date")
    @NotNull
    private LocalDateTime registrationDate;
}
