package com.cooksys.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.dto.ProjectDto;
import com.cooksys.mapper.ProjectMapper;
import com.cooksys.repository.ProjectRepository;

@Service
public class ProjectService {
	
	private ProjectRepository projectRepository;
	private ProjectMapper mapper;

	public ProjectService(ProjectRepository projectRepository, ProjectMapper mapper) {
		this.projectRepository = projectRepository;
		this.mapper = mapper;
	}

	public List<ProjectDto> getAll() {
		return projectRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public Long post(ProjectDto dto) {
		dto.setId(null);
		return projectRepository.save(mapper.toEntity(dto)).getId();
	}

	public List<ProjectDto> getOverdueProjects() {
		return projectRepository.findByendDateLessThan(System.currentTimeMillis()).stream().map(mapper::toDto).collect(Collectors.toList());
	}

}
