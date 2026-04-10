package com.hms.dao;

import java.util.*;

import com.hms.domain.Doctor;

public interface DoctorDAO {
	Optional<Doctor> findById(String id);

	void save(Doctor d);

	List<Doctor> findAll();
}
