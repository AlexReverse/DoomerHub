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

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "doomerhub", name = "education")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    @NotNull
    private UUID userId;

    @Column(name = "hei_name")
    @NotNull
    @Size(min = 3, max = 50)
    private String heiName;

    @Column(name = "education_start_date")
    @NotNull
    @Size(max = 4)
    private String educationStartDate;

    @Column(name = "education_end_date")
    @NotNull
    @Size(max = 4)
    private String educationEndDate;

    @Column(name = "specialization")
    @NotNull
    @Size(min = 3, max = 50)
    private String specialization;

    @Column(name = "form_education")
    @NotNull
    private FORM_EDUCATION formEducation;

    private enum FORM_EDUCATION {
        FULL_TIME("очная"),
        PART_TIME("очно-заочная"),
        DISTANCE("заочная");

        FORM_EDUCATION(String name) {
        }
    }
}
