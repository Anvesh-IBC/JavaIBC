package com.ibc.training.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibc.training.dto.ProjectRequest;
import com.ibc.training.entity.Project;
import com.ibc.training.exception.DuplicateException;
import com.ibc.training.exception.ResourceNotFoundException;
import com.ibc.training.repository.ProjectRepository;
import com.ibc.training.service.ProjectService;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
	private final ProjectRepository repo;

	public ProjectServiceImpl(ProjectRepository repo) {
		this.repo = repo;
	}

	public Project create(ProjectRequest request) {
		Project existing = repo.findByName(request.getName());
		if (existing != null) {
			throw new DuplicateException("Project already exists:" + request.getName());
		}
		Project p = new Project();
		p.setName(request.getName());
		return repo.save(p);
	}

	public Project update(Long id, ProjectRequest request) {
		Project p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
		Project byName = repo.findByName(request.getName());
		if (byName != null && !byName.getId().equals(id)) {
			throw new DuplicateException("Another project already named: " + request.getName());
		}
		p.setName(request.getName());
		return repo.save(p);
	}

	public void delete(Long id) {
		Project p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
		repo.delete(p);
	}

	@Transactional(readOnly = true)
	public Project get(Long id) {
		return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
	}

	@Transactional(readOnly = true)
	public List<Project> list() {
		return repo.findAll();
	}

	@Transactional(readOnly = true)
	public Project findByName(String name) {
		Project p = repo.findByName(name);
		if (p == null) {
			throw new ResourceNotFoundException("Project not found by name: " + name);
		}
		return p;
	}

}
