package com.hms.dao;

import java.util.*;

import com.hms.domain.Patient;

public interface PatientDAO {
	Optional<Patient> findById(String id);

	void save(Patient p);

	List<Patient> findAll();
}