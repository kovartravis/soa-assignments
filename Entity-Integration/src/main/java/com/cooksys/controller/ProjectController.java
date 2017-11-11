package com.cooksys.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.dto.ProjectDto;
import com.cooksys.service.ProjectService;

@RestController
@RequestMapping("project")
public class ProjectController {
	
	private ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	@GetMapping
	public List<ProjectDto> getAll(){
		return projectService.getAll();
	}
	
	@PostMapping
	public Long post(@RequestBody ProjectDto dto) {
		return projectService.post(dto);
	}
	
	@GetMapping("/overdue")
	public List<ProjectDto> getOverdueProjects(){
		return projectService.getOverdueProjects();
	}

}
