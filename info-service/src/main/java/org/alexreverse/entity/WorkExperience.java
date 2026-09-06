package org.alexreverse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "doomerhub", name = "work_experience")
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    @NotNull
    private UUID userId;

    @Column(name = "company_name")
    @NotNull
    @Size(min = 3, max = 50)
    private String companyName;

    @Column(name = "work_start_date")
    @NotNull
    private LocalDate workStartDate;

    @Column(name = "work_end_date")
    @NotNull
    private LocalDate workEndDate;

    @Column(name = "company_position")
    @NotNull
    @Size(min = 3, max = 50)
    private String companyPosition;

    @Column(name = "responsibilities")
    @Size(max = 100)
    private String responsibilities;
}
