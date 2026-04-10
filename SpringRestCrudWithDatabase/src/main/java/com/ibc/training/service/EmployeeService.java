package com.ibc.training.service;

import java.util.List;

import com.ibc.training.dto.EmployeeRequest;
import com.ibc.training.dto.EmployeeResponse;

public interface EmployeeService {
	EmployeeResponse create(EmployeeRequest request);

	EmployeeResponse update(Long id, EmployeeRequest request);

	void delete(Long id);

	EmployeeResponse get(Long id);

	List<EmployeeResponse> list();

	EmployeeResponse assignProjects(Long id, List<Long> projectIds);

	EmployeeResponse removeProjects(Long id, List<Long> projectIds);

	boolean existsByEmail(String email);
}
