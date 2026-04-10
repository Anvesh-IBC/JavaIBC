package com.hms.dao;

import java.util.*;
import com.hms.dao.AppointmentDAO;
import com.hms.domain.Appointment;

public class InMemoryAppointmentDAO implements AppointmentDAO {
	private final Map<String, Appointment> store = new HashMap<>();

	public Optional<Appointment> findById(String id) {
		return Optional.ofNullable(store.get(id));
	}

	public void save(Appointment a) {
		store.put(a.getId(), a);
	}

	public List<Appointment> findByDoctor(String doctorId) {
		List<Appointment> out = new ArrayList<>();
		for (Appointment a : store.values())
			if (a.getDoctor().getId().equals(doctorId))
				out.add(a);
		return out;
	}

	public List<Appointment> findByPatient(String patientId) {
		List<Appointment> out = new ArrayList<>();
		for (Appointment a : store.values())
			if (a.getPatient().getId().equals(patientId))
				out.add(a);
		return out;
	}
}
