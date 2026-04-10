package com.hms.concurrency.test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.hms.concurrency.CheckInQueue;
import com.hms.domain.Appointment;
import com.hms.domain.Doctor;
import com.hms.domain.Patient;

class CheckInQueueTest {

	@Test
	void submitAndTake_roundTrip() {
		CheckInQueue q = new CheckInQueue();
		Appointment a = new Appointment.Builder().id("A-1").patient(new Patient("P", "Arun"))
				.doctor(new Doctor("D", "Meera")).slot(LocalDateTime.now().plusMinutes(1)).build();
		q.submit(a);
		Appointment out = q.take();
		assertSame(a, out);
	}

	@Test
	void take_returnsNull_whenInterrupted() throws Exception {
		CheckInQueue q = new CheckInQueue();
		final Appointment[] got = new Appointment[1];
		Thread t = new Thread(() -> {
			got[0] = q.take(); // will block
		});
		t.start();
		Thread.sleep(100);
		t.interrupt();
		t.join(500);
		assertNull(got[0]); // Our take() returns null on interrupt
	}
}
