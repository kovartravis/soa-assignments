package com.cooksys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.entity.Project;
import com.cooksys.entity.ProjectManager;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{

	public List<Project> findAll();
	public Project save(Project project);
	public List<Project> findByendDateLessThan(Long currentTime);
    public List<Project> findByManager(ProjectManager manager);
}
