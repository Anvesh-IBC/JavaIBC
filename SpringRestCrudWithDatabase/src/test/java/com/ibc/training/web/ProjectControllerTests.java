package com.ibc.training.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibc.training.controller.ProjectController;
import com.ibc.training.dto.ProjectRequest;
import com.ibc.training.entity.Project;
import com.ibc.training.exception.DuplicateException;
import com.ibc.training.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(controllers = ProjectController.class)
@ActiveProfiles("test")
public class ProjectControllerTests {

	@Autowired
	MockMvc mvc;
	@Autowired
	ObjectMapper om;

	@MockBean
	ProjectService projectService;

	@Test
	void createProject_duplicateName_conflict409() throws Exception {
		ProjectRequest req = new ProjectRequest();
		req.setName("Alpha");

		// when(projectService.create(req)).thenThrow(new DuplicateException("Project
		// already exists: Alpha"));
		when(projectService.create(any(ProjectRequest.class)))
				.thenThrow(new DuplicateException("Project already exists: Alpha"));

		mvc.perform(post("/api/projects").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(req)))
				.andExpect(status().isConflict()).andExpect(jsonPath("$.error").value("DUPLICATE"));
	}

	@Test
	void createProject_success() throws Exception {
		ProjectRequest req = new ProjectRequest();
		req.setName("Beta");

		Project p = new Project("Beta");
		p.setId(1L);

		// when(projectService.create(req)).thenReturn(p);
		when(projectService.create(any(ProjectRequest.class))).thenReturn(p);

		mvc.perform(post("/api/projects").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(req)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Beta"));
	}
}