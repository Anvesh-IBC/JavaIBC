package com.ibc.training.repository;

import com.ibc.training.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	Project findByName(String name);
}