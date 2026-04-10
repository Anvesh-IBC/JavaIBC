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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibc.training.dto.EmployeeRequest;
import com.ibc.training.dto.EmployeeResponse;
import com.ibc.training.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/employees", produces = "application/json")
@Tag(name = "Employees", description = "Employee management APIs")
public class EmployeeController {
	private final EmployeeService service;

	public EmployeeController(EmployeeService service) {
		this.service = service;
	}

	@PostMapping(produces = "application/json")
	@Operation(summary = "Create Employee")
	public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody EmployeeRequest req) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update Employee")
	public ResponseEntity<EmployeeResponse> update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest req) {
		return ResponseEntity.ok(service.update(id, req));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Employee")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get Employee by ID")
	public ResponseEntity<EmployeeResponse> get(@PathVariable Long id) {
		return ResponseEntity.ok(service.get(id));
	}

	@GetMapping
	@Operation(summary = "List Employees")
	public ResponseEntity<List<EmployeeResponse>> list() {
		return ResponseEntity.ok(service.list());
	}

	@PostMapping("/{id}/projects:assign")
	@Operation(summary = "Assign Projects to Employee")
	public ResponseEntity<EmployeeResponse> assign(@PathVariable Long id, @RequestBody List<Long> projectIds) {
		return ResponseEntity.ok(service.assignProjects(id, projectIds));
	}

	@PostMapping("/{id}/projects:remove")
	@Operation(summary = "Remove Projects from Employee")
	public ResponseEntity<EmployeeResponse> remove(@PathVariable Long id, @RequestBody List<Long> projectIds) {
		return ResponseEntity.ok(service.removeProjects(id, projectIds));
	}

	@GetMapping("/exists-by-email")
	@Operation(summary = "Check if Email exists")
	public ResponseEntity<Boolean> existsByEmail(@RequestParam("email") String email) {
		return ResponseEntity.ok(service.existsByEmail(email));
	}

}
