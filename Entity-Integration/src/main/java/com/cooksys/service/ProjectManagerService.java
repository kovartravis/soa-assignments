package com.cooksys.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.dto.ProjectDto;
import com.cooksys.dto.ProjectManagerDto;
import com.cooksys.entity.Project;
import com.cooksys.entity.ProjectManager;
import com.cooksys.exception.ReferencedEntityNotFoundException;
import com.cooksys.mapper.ProjectManagerMapper;
import com.cooksys.mapper.ProjectMapper;
import com.cooksys.repository.ProjectManagerRepository;
import com.cooksys.repository.ProjectRepository;

@Service
public class ProjectManagerService {

	private ProjectManagerRepository repo;
	private ProjectRepository projectRepo;
	private ProjectManagerMapper mapper;
	private ProjectMapper projectMapper;

	public ProjectManagerService(ProjectManagerRepository repo, ProjectManagerMapper mapper, ProjectRepository projectRepo, ProjectMapper projectMapper) {
		this.repo = repo;
		this.mapper = mapper;
		this.projectRepo = projectRepo;
		this.projectMapper = projectMapper;
		
		Set<Project> travisProjects = new HashSet<Project>();
		ProjectManager travis = new ProjectManager(null, "Travis", "Kovar", null);
		travisProjects.add(new Project(null, (long) 0, System.currentTimeMillis(), travis));
		travis.setProjects(travisProjects);
		
		Set<Project> bobProjects = new HashSet<Project>();
		ProjectManager bob = new ProjectManager(null, "Bob", "Boberson", null);
		bobProjects.add(new Project(null, (long) 0, Long.MAX_VALUE, bob));
		bobProjects.add(new Project(null, (long) 0, (long) 2132, bob));
		bobProjects.add(new Project(null, (long) 0, (long) 2312, bob));
		bobProjects.add(new Project(null, (long) 0, (long) 2, bob));
		bobProjects.add(new Project(null, (long) 0, (long) 1312, bob));
		bob.setProjects(bobProjects);
		
		Set<Project> larryProjects = new HashSet<Project>();
		ProjectManager larry = new ProjectManager(null, "larry", "berry", null);
		larryProjects.add(new Project(null, (long) 0, (long) 12311, larry));
		larryProjects.add(new Project(null, (long) 0, (long) 11231, larry));
		larryProjects.add(new Project(null, (long) 0, (long) 12131, larry));
		larry.setProjects(larryProjects);

		repo.save(travis);
		repo.save(bob);
		repo.save(larry);
		projectRepo.save(travisProjects);
		projectRepo.save(bobProjects);
		projectRepo.save(larryProjects);
	}
	
	public List<ProjectManagerDto> getAll() {
		return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}

	public boolean has(Long id) {
		return repo.existsById(id);
	}

	public ProjectManagerDto get(Long id) throws ReferencedEntityNotFoundException{
		mustExist(id);
		return mapper.toDto(repo.findOne(id));
	}

	public Long post(ProjectManagerDto projectManagerDto) {
		projectManagerDto.setId(null);
		return repo.save(mapper.toEntity(projectManagerDto)).getId();
	}

	public void put(Long id, ProjectManagerDto projectManagerDto) throws ReferencedEntityNotFoundException{
		mustExist(id);
		projectManagerDto.setId(id);
		repo.save(mapper.toEntity(projectManagerDto));
	}
	
	private void mustExist(Long id) throws ReferencedEntityNotFoundException{
		if(!has(id))
			throw new ReferencedEntityNotFoundException(ProjectManager.class, id);
	}

	public void delete(Long id) throws ReferencedEntityNotFoundException{
		mustExist(id);
		repo.delete(id);
	}

	public List<ProjectDto> getAllProjects(Long id) throws ReferencedEntityNotFoundException{
		mustExist(id);
		return projectRepo.findByManager(repo.findOne(id)).stream().map(projectMapper::toDto).collect(Collectors.toList());
	}

	public List<ProjectManagerDto> getAllProjectManagersWithOverdueProjects() {
		return repo.findDistinctByProjects_endDateLessThan(System.currentTimeMillis()).stream()
				           .sorted( (a, b) -> {if(a.getProjects().size() > b.getProjects().size()) return -1;
				                               else return 1;} ).map(mapper::toDto).collect(Collectors.toList());
	}
}
