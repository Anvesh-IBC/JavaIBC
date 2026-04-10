package com.hms.dao;

import java.util.*;

import com.hms.domain.Appointment;

public interface AppointmentDAO {
	Optional<Appointment> findById(String id);

	void save(Appointment a);

	List<Appointment> findByDoctor(String doctorId);

	List<Appointment> findByPatient(String patientId);
}
