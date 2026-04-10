package com.ibc.training.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ibc.training.dto.ProjectRequest;
import com.ibc.training.entity.Project;
import com.ibc.training.exception.DuplicateException;
import com.ibc.training.exception.ResourceNotFoundException;

@SpringBootTest
@ActiveProfiles("test")
public class ProjectServiceTests {

	@Autowired
	ProjectService projectService;

	@Test
	void createProject_duplicate_shouldThrowException() {

		ProjectRequest req = new ProjectRequest();
		req.setName("AI Project");

		projectService.create(req);

		assertThatThrownBy(() -> projectService.create(req)).isInstanceOf(DuplicateException.class);
	}
	
	@Test
	void deleteProject_success() {

	    ProjectRequest req = new ProjectRequest();
	    req.setName("Test Project");

	    Project p = projectService.create(req);

	    projectService.delete(p.getId());

	    assertThatThrownBy(() -> projectService.get(p.getId()))
	            .isInstanceOf(ResourceNotFoundException.class);
	}

}
