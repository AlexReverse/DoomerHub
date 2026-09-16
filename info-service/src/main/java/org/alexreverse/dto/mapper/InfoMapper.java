package org.alexreverse.dto.mapper;

import org.alexreverse.controller.payload.*;
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

    default void authorPayloadPatchEntity(AuthorInformationPayload payload, @MappingTarget AuthorInformation entity) {
        entity.setNew(false);
    }

    @AfterMapping
    default void handleIsNew(@MappingTarget AuthorInformation entity) {
        entity.setNew(true);
    }

    EducationDto educationToDto(Education education);

    Education payloadToEducation(UUID userId, EducationPayload payload);

    void educationPayloadPatchEntity(EducationPatchPayload payload, @MappingTarget Education entity);

    WorkExperienceDto workToDto(WorkExperience workExperience);

    WorkExperience payloadToWorkExperience(UUID userId, WorkExperiencePayload payload);

    void workExperiencePayloadPatchEntity(WorkExperiencePatchPayload payload, @MappingTarget WorkExperience entity);
}
