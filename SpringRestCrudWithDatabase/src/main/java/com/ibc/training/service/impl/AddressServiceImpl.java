package com.ibc.training.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibc.training.dto.AddressRequest;
import com.ibc.training.entity.Address;
import com.ibc.training.entity.Employee;
import com.ibc.training.exception.ResourceNotFoundException;
import com.ibc.training.repository.AddressRepository;
import com.ibc.training.repository.EmployeeRepository;
import com.ibc.training.service.AddressService;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
	private final AddressRepository repo;
	private final EmployeeRepository employeeRepo;

	public AddressServiceImpl(AddressRepository repo, EmployeeRepository employeeRepo) {
		this.repo = repo;
		this.employeeRepo = employeeRepo;
	}

	public Address create(AddressRequest request) {
		Employee e = employeeRepo.findById(request.getEmployeeId())
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + request.getEmployeeId()));

		Address a = new Address();
		a.setStreet(request.getStreet());
		a.setCity(request.getCity());
		a.setEmployee(e);
		// tie both sides
		e.setAddress(a);
		return repo.save(a);

	}

	public Address update(Long id, AddressRequest request) {
		Address a = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found: " + id));
		a.setStreet(request.getStreet());
		a.setCity(request.getCity());
		if (request.getEmployeeId() != null) {
			Employee e = employeeRepo.findById(request.getEmployeeId())
					.orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + request.getEmployeeId()));
			a.setEmployee(e);
			e.setAddress(a);
		}
		return repo.save(a);

	}

	public void delete(Long id) {
		Address a = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found: " + id));
		Employee e = a.getEmployee();
		if (e != null) {
			e.setAddress(null);
		}
		repo.delete(a);
	}

	@Transactional(readOnly = true)
	public Address get(Long id) {
		return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found: " + id));
	}

}
