package com.hms.dao;

import java.util.*;
import com.hms.dao.DoctorDAO;
import com.hms.domain.Doctor;

public class InMemoryDoctorDAO implements DoctorDAO {
	private final Map<String, Doctor> store = new HashMap<>();

	public Optional<Doctor> findById(String id) {
		return Optional.ofNullable(store.get(id));
	}

	public void save(Doctor d) {
		store.put(d.getId(), d);
	}

	public List<Doctor> findAll() {
		return new ArrayList<>(store.values());
	}
}