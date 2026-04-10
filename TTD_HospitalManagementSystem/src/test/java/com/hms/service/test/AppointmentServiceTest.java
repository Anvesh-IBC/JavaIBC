package com.hms.service.test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hms.dao.AppointmentDAO;
import com.hms.dao.DoctorDAO;
import com.hms.dao.PatientDAO;
import com.hms.domain.Appointment;
import com.hms.domain.AppointmentStatus;
import com.hms.domain.Doctor;
import com.hms.domain.Patient;
import com.hms.exceptions.NotFoundException;
import com.hms.exceptions.OverbookingException;
import com.hms.exceptions.ValidationException;
import com.hms.service.AppointmentService;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

	@Mock
	AppointmentDAO apptDAO;
	@Mock
	DoctorDAO doctorDAO;
	@Mock
	PatientDAO patientDAO;

	AppointmentService service;
	Doctor doc;
	Patient pat;

	@BeforeEach
	void setUp() {
		service = new AppointmentService(apptDAO, doctorDAO, patientDAO);
		doc = new Doctor("D-11", "Dr.Meera");
		pat = new Patient("P-1001", "Arun");
	}

	private Appointment makeFutureAppt() {
		return new Appointment.Builder().id("A-1").doctor(doc).patient(pat).slot(LocalDateTime.now().plusMinutes(10))
				.build();
	}

	@Test
	void schedule_success_savesAppointment() throws Exception {
		Appointment appt = makeFutureAppt();

		when(doctorDAO.findById("D-11")).thenReturn(Optional.of(doc));
		when(patientDAO.findById("P-1001")).thenReturn(Optional.of(pat));
		when(apptDAO.findByDoctor("D-11")).thenReturn(Collections.emptyList());

		Appointment res = service.schedule(appt);

		assertSame(appt, res);
		verify(apptDAO).save(appt);
	}

	@Test
	void schedule_fails_whenSlotInPast() {
		Appointment past = new Appointment.Builder().id("A-2").doctor(doc).patient(pat)
				.slot(LocalDateTime.now().minusMinutes(1)).build();

		// No stubs needed: validate() will fail on slot before calling DAOs
		assertThrows(ValidationException.class, () -> service.schedule(past));
		verify(apptDAO, never()).save(any());
	}

	@Test
	void schedule_fails_whenDoctorMissing() {
		Appointment appt = makeFutureAppt();

		when(doctorDAO.findById("D-11")).thenReturn(Optional.empty());
		// Do NOT stub patientDAO here: method throws before reaching it

		assertThrows(NotFoundException.class, () -> service.schedule(appt));
		verify(apptDAO, never()).save(any());
	}

	@Test
	void schedule_fails_whenOverlap() {
		Appointment appt = makeFutureAppt();

		when(doctorDAO.findById("D-11")).thenReturn(Optional.of(doc));
		when(patientDAO.findById("P-1001")).thenReturn(Optional.of(pat));

		Appointment existing = new Appointment.Builder().id("A-EXIST").doctor(doc).patient(pat).slot(appt.getSlot())
				.build();
		existing.setStatus(AppointmentStatus.SCHEDULED);

		when(apptDAO.findByDoctor("D-11")).thenReturn(Collections.singletonList(existing));

		assertThrows(OverbookingException.class, () -> service.schedule(appt));
		verify(apptDAO, never()).save(any());
	}
}
