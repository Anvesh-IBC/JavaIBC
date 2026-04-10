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

import com.ibc.training.dto.DepartmentRequest;
import com.ibc.training.entity.Department;
import com.ibc.training.service.DepartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/departments")
@Tag(name = "Departments", description = "Department management APIs")
public class DepartmentController {

	private final DepartmentService service;

	public DepartmentController(DepartmentService service) {
		this.service = service;
	}

	@PostMapping
	@Operation(summary = "Create Department")
	public ResponseEntity<Department> create(@Valid @RequestBody DepartmentRequest req) {
		Department d = service.create(req);
		return ResponseEntity.status(HttpStatus.CREATED).body(d);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update Department")
	public ResponseEntity<Department> update(@PathVariable Long id, @Valid @RequestBody DepartmentRequest req) {
		return ResponseEntity.ok(service.update(id, req));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Department")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get Department by ID")
	public ResponseEntity<Department> get(@PathVariable Long id) {
		return ResponseEntity.ok(service.get(id));
	}

	@GetMapping
	@Operation(summary = "List Departments")
	public ResponseEntity<List<Department>> list() {
		return ResponseEntity.ok(service.list());
	}
}
