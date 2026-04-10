package com.ibc.training.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibc.training.dto.ProjectRequest;
import com.ibc.training.entity.Project;
import com.ibc.training.service.ProjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/projects")
@Tag(name = "Projects", description = "Project management APIs")
public class ProjectController {

	private final ProjectService service;

	public ProjectController(ProjectService service) {
		this.service = service;
	}

	@PostMapping
	@Operation(summary = "Create Project")
	public ResponseEntity<Project> create(@Valid @RequestBody ProjectRequest req) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update Project")
	public ResponseEntity<Project> update(@PathVariable Long id, @Valid @RequestBody ProjectRequest req) {
		return ResponseEntity.ok(service.update(id, req));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Project")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get Project by ID")
	public ResponseEntity<Project> get(@PathVariable Long id) {
		return ResponseEntity.ok(service.get(id));
	}

	@GetMapping
	@Operation(summary = "List Projects")
	public ResponseEntity<List<Project>> list() {
		return ResponseEntity.ok(service.list());
	}

}
