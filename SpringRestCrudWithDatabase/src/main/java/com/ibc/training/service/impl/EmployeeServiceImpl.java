package com.ibc.training.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ibc.training.service.EmployeeService;
import com.ibc.training.dto.EmployeeRequest;
import com.ibc.training.dto.EmployeeResponse;
import com.ibc.training.entity.*;
import com.ibc.training.exception.DuplicateException;
import com.ibc.training.exception.ResourceNotFoundException;
import com.ibc.training.repository.*;

import java.util.ArrayList;
import java.util.List;
//import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepo;
	private final DepartmentRepository departmentRepo;
	private final ProjectRepository projectRepo;
	private final AddressRepository addressRepo;

	public EmployeeServiceImpl(EmployeeRepository employeeRepo, DepartmentRepository departmentRepo,
			ProjectRepository projectRepo, AddressRepository addressRepo) {
		this.employeeRepo = employeeRepo;
		this.departmentRepo = departmentRepo;
		this.projectRepo = projectRepo;
		this.addressRepo = addressRepo;
	}

	public EmployeeResponse create(EmployeeRequest request) {
		if (employeeRepo.existsByEmail(request.getEmail())) {
			throw new DuplicateException("Email already in use: " + request.getEmail());
		}
		Department dept = departmentRepo.findById(request.getDepartmentId())
				.orElseThrow(() -> new ResourceNotFoundException("Department not found: " + request.getDepartmentId()));
		Employee e = new Employee();
		e.setName(request.getName());
		e.setEmail(request.getEmail());
		e.setSalary(request.getSalary());
		e.setDepartment(dept);

		// Address (optional)
		if (request.getAddressStreet() != null || request.getAddressCity() != null) {
			Address a = new Address();
			a.setStreet(request.getAddressStreet());
			a.setCity(request.getAddressCity());
			e.setAddress(a); // sets both sides
		}

		// Projects (optional)
		if (request.getProjectIds() != null) {
			int i;
			for (i = 0; i < request.getProjectIds().size(); i++) {
				Long pid = request.getProjectIds().get(i);
				if (pid != null) {
					Project p = projectRepo.findById(pid)
							.orElseThrow(() -> new ResourceNotFoundException("Project not found: " + pid));
					e.addProject(p);
				}
			}
		}

		Employee saved = employeeRepo.save(e);
		// if address present, ensure persisted
		if (saved.getAddress() != null && saved.getAddress().getId() == null) {
			addressRepo.save(saved.getAddress());
		}
		return toResponse(saved);
	}

	public EmployeeResponse update(Long id, EmployeeRequest request) {
		Employee e = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
		if (!e.getEmail().equals(request.getEmail())) {
			if (employeeRepo.existsByEmail(request.getEmail())) {
				throw new DuplicateException("Email already in use: " + request.getEmail());
			}
		}
		Department dept = departmentRepo.findById(request.getDepartmentId())
				.orElseThrow(() -> new ResourceNotFoundException("Department not found: " + request.getDepartmentId()));
		e.setName(request.getName());
		e.setEmail(request.getEmail());
		e.setSalary(request.getSalary());
		e.setDepartment(dept);

		// Update/Set address if provided values present
		if (request.getAddressStreet() != null || request.getAddressCity() != null) {
			Address a = e.getAddress();
			if (a == null) {
				a = new Address();
				e.setAddress(a);
			}
			a.setStreet(request.getAddressStreet());
			a.setCity(request.getAddressCity());
		}

		// Replace projects if projectIds provided
		if (request.getProjectIds() != null) {
			// clear existing
			List<Project> toRemove = new ArrayList<Project>();
			int i;
			for (i = 0; i < e.getProjects().size(); i++) {
				toRemove.add(e.getProjects().get(i));
			}
			for (i = 0; i < toRemove.size(); i++) {
				e.removeProject(toRemove.get(i));
			}
			for (i = 0; i < request.getProjectIds().size(); i++) {
				Long pid = request.getProjectIds().get(i);
				if (pid != null) {
					Project p = projectRepo.findById(pid)
							.orElseThrow(() -> new ResourceNotFoundException("Project not found: " + pid));
					e.addProject(p);
				}
			}
		}
		Employee saved = employeeRepo.save(e);
		return toResponse(saved);
	}

	public void delete(Long id) {
		Employee e = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
		// break relationships
		if (e.getAddress() != null) {
			Address a = e.getAddress();
			e.setAddress(null);
			addressRepo.delete(a);
		}
		List<Project> toRemove = new ArrayList<Project>();
		int i;
		for (i = 0; i < e.getProjects().size(); i++) {
			toRemove.add(e.getProjects().get(i));
		}
		for (i = 0; i < toRemove.size(); i++) {
			e.removeProject(toRemove.get(i));
		}
		employeeRepo.delete(e);
	}

	@Transactional(readOnly = true)
	public EmployeeResponse get(Long id) {
		Employee e = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
		return toResponse(e);
	}

	@Transactional(readOnly = true)
	public List<EmployeeResponse> list() {
		List<Employee> list = employeeRepo.findAll();
		List<EmployeeResponse> out = new ArrayList<EmployeeResponse>();
		int i;
		for (i = 0; i < list.size(); i++) {
			out.add(toResponse(list.get(i)));
		}
		return out;
	}

	public EmployeeResponse assignProjects(Long id, List<Long> projectIds) {
		Employee e = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
		if (projectIds != null) {
			int i;
			for (i = 0; i < projectIds.size(); i++) {
				Long pid = projectIds.get(i);
				if (pid != null) {
					Project p = projectRepo.findById(pid)
							.orElseThrow(() -> new ResourceNotFoundException("Project not found: " + pid));
					e.addProject(p);
				}
			}
		}
		return toResponse(employeeRepo.save(e));
	}

	public EmployeeResponse removeProjects(Long id, List<Long> projectIds) {
		Employee e = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
		if (projectIds != null) {
			int i, j;
			for (i = 0; i < projectIds.size(); i++) {
				Long pid = projectIds.get(i);
				if (pid != null) {
					// find project from current list
					Project found = null;
					for (j = 0; j < e.getProjects().size(); j++) {
						if (e.getProjects().get(j).getId().equals(pid)) {
							found = e.getProjects().get(j);
							break;
						}
					}
					if (found != null) {
						e.removeProject(found);
					}
				}
			}
		}
		return toResponse(employeeRepo.save(e));
	}

	@Transactional(readOnly = true)
	public boolean existsByEmail(String email) {
		return employeeRepo.existsByEmail(email);
	}

	// --- mapper (no streams) ---
	private EmployeeResponse toResponse(Employee e) {
		EmployeeResponse r = new EmployeeResponse();
		r.setId(e.getId());
		r.setName(e.getName());
		r.setEmail(e.getEmail());
		r.setSalary(e.getSalary());
		if (e.getDepartment() != null) {
			r.setDepartmentId(e.getDepartment().getId());
			r.setDepartmentName(e.getDepartment().getName());
		}
		if (e.getAddress() != null) {
			r.setAddressStreet(e.getAddress().getStreet());
			r.setAddressCity(e.getAddress().getCity());
		}
		int i;
		for (i = 0; i < e.getProjects().size(); i++) {
			Project p = e.getProjects().get(i);
			r.getProjectIds().add(p.getId());
			r.getProjectNames().add(p.getName());
		}
		return r;
	}
}