package org.alexreverse.dto.mapper;

import org.alexreverse.dto.AuthorInformationDto;
import org.alexreverse.dto.EducationDto;
import org.alexreverse.dto.WorkExperienceDto;
import org.alexreverse.entity.AuthorInformation;
import org.alexreverse.entity.Education;
import org.alexreverse.entity.WorkExperience;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InfoMapper {

    EducationDto educationToDto(Education education);

    AuthorInformationDto authorToDto(AuthorInformation authorInformation);

    WorkExperienceDto workToDto(WorkExperience workExperience);
}
