package com.ibc.training.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibc.training.dto.DepartmentRequest;
import com.ibc.training.entity.Department;
import com.ibc.training.exception.DuplicateException;
import com.ibc.training.exception.ResourceNotFoundException;
import com.ibc.training.repository.DepartmentRepository;
import com.ibc.training.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
	private final DepartmentRepository repo;

	public DepartmentServiceImpl(DepartmentRepository repo) {
		this.repo = repo;
	}

	public Department create(DepartmentRequest request) {
		Department existing = repo.findByName(request.getName());
		if (existing != null) {
			throw new DuplicateException("Department already exists:" + request.getName());
		}
		Department d = new Department();
		d.setName(request.getName());
		return repo.save(d);
	}

	public Department update(Long id, DepartmentRequest request) {
		Department d = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found: " + id));
		Department byName = repo.findByName(request.getName());
		if (byName != null && !byName.getId().equals(id)) {
			throw new DuplicateException("Another department already named:" + request.getName());
		}
		d.setName(request.getName());
		return repo.save(d);
	}

	public void delete(Long id) {
		Department d = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found:" + id));
		repo.delete(d);
	}

	@Transactional(readOnly = true)
	public Department get(Long id) {
		return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found: " + id));
	}

	@Transactional(readOnly = true)
	public List<Department> list() {
		return repo.findAll();
	}

	@Transactional(readOnly = true)
	public Department findByName(String name) {
		Department d = repo.findByName(name);
		if (d == null) {
			throw new ResourceNotFoundException("Department not found by name: " + name);
		}
		return d;

	}

}
