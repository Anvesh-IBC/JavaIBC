package com.hms.patterns.observer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import com.hms.domain.Appointment;
import com.hms.domain.Doctor;
import com.hms.domain.Patient;
import com.hms.patterns.observer.AppointmentEventBus;
import com.hms.patterns.observer.AppointmentListener;

class AppointmentEventBusTest {

	@Test
	void listenersReceiveBothEvents() {
		AppointmentEventBus bus = new AppointmentEventBus();
		AtomicInteger scheduled = new AtomicInteger();
		AtomicInteger completed = new AtomicInteger();

		bus.register(new AppointmentListener() {
			@Override
			public void onScheduled(Appointment a) {
				scheduled.incrementAndGet();
			}

			@Override
			public void onCompleted(Appointment a) {
				completed.incrementAndGet();
			}
		});

		Appointment a = new Appointment.Builder().id("A-1").patient(new Patient("P-1", "Arun"))
				.doctor(new Doctor("D-1", "Meera")).slot(LocalDateTime.now().plusMinutes(10)).build();

		bus.fireScheduled(a);
		bus.fireCompleted(a);

		assertEquals(1, scheduled.get());
		assertEquals(1, completed.get());
	}
}
