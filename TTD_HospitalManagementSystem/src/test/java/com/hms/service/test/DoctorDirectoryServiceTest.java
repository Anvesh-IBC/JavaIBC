package com.hms.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hms.dao.DoctorDAO;
import com.hms.domain.Doctor;
import com.hms.domain.Specialization;
import com.hms.service.DoctorDirectoryService;

@ExtendWith(MockitoExtension.class)
class DoctorDirectoryServiceTest {

	@Mock
	DoctorDAO dao;

	@Test
	void filtersAndSortsByName() {
		Doctor a = new Doctor("1", "Anita");
		a.addSpecialization(Specialization.CARDIOLOGY);
		Doctor c = new Doctor("3", "Chirag");
		c.addSpecialization(Specialization.CARDIOLOGY);
		Doctor b = new Doctor("2", "Bala");
		b.addSpecialization(Specialization.ORTHOPEDICS);

		when(dao.findAll()).thenReturn(Arrays.asList(a, b, c));
		DoctorDirectoryService svc = new DoctorDirectoryService(dao);

		List<Doctor> cardiologists = svc.findBySpecialization(Specialization.CARDIOLOGY);
		assertEquals(Arrays.asList(a, c), cardiologists);

		NavigableSet<String> names = svc.doctorNamesSorted();
		assertEquals(new TreeSet<>(Arrays.asList("Anita", "Bala", "Chirag")), names);
	}
}
