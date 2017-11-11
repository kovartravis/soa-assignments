package com.cooksys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.ProjectManager;

public interface ProjectManagerRepository extends JpaRepository<ProjectManager, Long>{
	
	public boolean existsById(Long primaryKey);
	public ProjectManager save(ProjectManager project);
	public List<ProjectManager> findAll();
	public ProjectManager findOne(Long primaryKey);
	public void delete(Long primaryKey);
	public List<ProjectManager> findDistinctByProjects_endDateLessThan(Long currentTime);

}
