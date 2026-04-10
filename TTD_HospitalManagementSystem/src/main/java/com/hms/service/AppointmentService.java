package com.hms.service;

import java.time.LocalDateTime;
import java.util.List;

import com.hms.dao.*;
import com.hms.domain.*;
import com.hms.exceptions.*;

public class AppointmentService {
	private final AppointmentDAO apptDAO;
	private final DoctorDAO doctorDAO;
	private final PatientDAO patientDAO;

	public AppointmentService(AppointmentDAO a, DoctorDAO d, PatientDAO p) {
		this.apptDAO = a;
		this.doctorDAO = d;
		this.patientDAO = p;
	}

	public Appointment schedule(Appointment appt) throws ValidationException, OverbookingException {
		validate(appt);
		ensureNoOverlap(appt.getDoctor().getId(), appt.getSlot());
		apptDAO.save(appt);
		return appt;
	}
	
	public void complete(String apptId) {
		// Missing

	}

	private void validate(Appointment a) throws ValidationException {
		if (a.getSlot().isBefore(LocalDateTime.now()))
			throw new ValidationException("Slot is in the past");
		doctorDAO.findById(a.getDoctor().getId()).orElseThrow(() -> new NotFoundException("Doctor missing"));
		patientDAO.findById(a.getPatient().getId()).orElseThrow(() -> new NotFoundException("Patient missing"));
	}

	private void ensureNoOverlap(String doctorId, LocalDateTime slot) throws OverbookingException {
		List<Appointment> doctorAppts = apptDAO.findByDoctor(doctorId);
		for (Appointment ex : doctorAppts)
			if (ex.getSlot().equals(slot) && ex.getStatus() == AppointmentStatus.SCHEDULED)
				throw new OverbookingException("Doctor already booked at " + slot);
	}

}