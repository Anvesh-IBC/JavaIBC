package com.ibc.training.service;

import java.util.List;

import com.ibc.training.dto.DepartmentRequest;
import com.ibc.training.entity.Department;

public interface DepartmentService {
	Department create(DepartmentRequest request);

	Department update(Long id, DepartmentRequest request);

	void delete(Long id);

	Department get(Long id);

	List<Department> list();

	Department findByName(String name);
}
