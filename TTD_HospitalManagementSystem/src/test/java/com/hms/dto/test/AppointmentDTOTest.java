package com.hms.dto.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.hms.dto.AppointmentDTO;

class AppointmentDTOTest {

	@Test
	void serializesAndDeserializes() throws Exception {
		AppointmentDTO dto = new AppointmentDTO();
		dto.apptId = "A-1";
		dto.patientId = "P-1";
		dto.doctorId = "D-1";
		dto.slot = LocalDateTime.now().plusMinutes(5);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			oos.writeObject(dto);
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		AppointmentDTO copy;
		try (ObjectInputStream ois = new ObjectInputStream(bis)) {
			copy = (AppointmentDTO) ois.readObject();
		}

		assertEquals(dto.apptId, copy.apptId);
		assertEquals(dto.patientId, copy.patientId);
		assertEquals(dto.doctorId, copy.doctorId);
		assertEquals(dto.slot, copy.slot);
	}
}
