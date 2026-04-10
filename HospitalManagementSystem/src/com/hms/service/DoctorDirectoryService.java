package com.hms.service;

import com.hms.dao.DoctorDAO;
import com.hms.domain.*;

import java.util.*;
import java.util.stream.*;

public class DoctorDirectoryService {
	private final DoctorDAO doctorDAO;

	public DoctorDirectoryService(DoctorDAO dao) {
		this.doctorDAO = dao;
	}

	public List<Doctor> findBySpecialization(Specialization s) {
		return doctorDAO.findAll().stream().filter(d -> d.getSpecializations().contains(s))
				.sorted(Comparator.comparing(Doctor::getName)).collect(Collectors.toList());
	}

	public NavigableSet<String> doctorNamesSorted() {
		return new TreeSet<>(doctorDAO.findAll().stream().map(Doctor::getName).collect(Collectors.toList()));
	}
}
