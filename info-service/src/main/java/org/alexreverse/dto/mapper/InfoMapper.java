package org.alexreverse.dto.mapper;

import org.alexreverse.controller.payload.AuthorInformationPayload;
import org.alexreverse.controller.payload.EducationPayload;
import org.alexreverse.controller.payload.WorkExperiencePayload;
import org.alexreverse.dto.AuthorInformationDto;
import org.alexreverse.dto.EducationDto;
import org.alexreverse.dto.WorkExperienceDto;
import org.alexreverse.entity.AuthorInformation;
import org.alexreverse.entity.Education;
import org.alexreverse.entity.WorkExperience;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface InfoMapper {

    AuthorInformationDto authorToDto(AuthorInformation authorInformation);

    AuthorInformation authorPayloadToEntity(UUID userId, AuthorInformationPayload payload,
                                            LocalDateTime registrationDate);

    @AfterMapping
    default void handleIsNew(@MappingTarget AuthorInformation entity) {
        entity.setNew(true);
    }

    EducationDto educationToDto(Education education);

    Education payloadToEducation(UUID userId, EducationPayload payload);

    WorkExperienceDto workToDto(WorkExperience workExperience);

    WorkExperience payloadToWorkExperience(UUID userId, WorkExperiencePayload payload);
}
