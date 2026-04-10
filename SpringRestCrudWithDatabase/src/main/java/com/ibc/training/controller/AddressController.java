package com.ibc.training.controller;

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

import com.ibc.training.dto.AddressRequest;
import com.ibc.training.entity.Address;
import com.ibc.training.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/addresses")
@Tag(name = "Addresses", description = "Address management APIs")
public class AddressController {

	private final AddressService service;

	public AddressController(AddressService service) {
		this.service = service;
	}

	@PostMapping
	@Operation(summary = "Create Address for an Employee")
	public ResponseEntity<Address> create(@Valid @RequestBody AddressRequest req) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update Address")
	public ResponseEntity<Address> update(@PathVariable Long id, @Valid @RequestBody AddressRequest req) {
		return ResponseEntity.ok(service.update(id, req));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Address")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get Address by ID")
	public ResponseEntity<Address> get(@PathVariable Long id) {
		return ResponseEntity.ok(service.get(id));
	}

}
