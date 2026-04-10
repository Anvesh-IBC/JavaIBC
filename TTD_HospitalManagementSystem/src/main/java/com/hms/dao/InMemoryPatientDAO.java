package com.hms.dao;

import java.util.*;

import com.hms.dao.PatientDAO;
import com.hms.domain.Patient;

public class InMemoryPatientDAO implements PatientDAO {
	private final Map<String, Patient> store = new HashMap<>();

	public Optional<Patient> findById(String id) {
		return Optional.ofNullable(store.get(id));
	}

	public void save(Patient p) {
		store.put(p.getId(), p);
	}

	public List<Patient> findAll() {
		return new ArrayList<>(store.values());
	}
}
