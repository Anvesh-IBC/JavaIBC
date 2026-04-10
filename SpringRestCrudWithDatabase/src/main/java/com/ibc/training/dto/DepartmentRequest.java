package com.ibc.training.dto;

import jakarta.validation.constraints.NotBlank;

public class DepartmentRequest {
	@NotBlank
	private String name;

	public DepartmentRequest() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
