package com.cooksys.mapper;

import org.mapstruct.Mapper;

import com.cooksys.dto.ProjectDto;
import com.cooksys.entity.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

	Project toEntity(ProjectDto dto);
	
	ProjectDto toDto(Project project);
}
