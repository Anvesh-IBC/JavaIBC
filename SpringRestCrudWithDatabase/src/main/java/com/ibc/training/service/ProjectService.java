package com.ibc.training.service;

import java.util.List;

import com.ibc.training.dto.ProjectRequest;
import com.ibc.training.entity.Project;

public interface ProjectService {
	Project create(ProjectRequest request);

	Project update(Long id, ProjectRequest request);

	void delete(Long id);

	Project get(Long id);

	List<Project> list();

	Project findByName(String name);
}
