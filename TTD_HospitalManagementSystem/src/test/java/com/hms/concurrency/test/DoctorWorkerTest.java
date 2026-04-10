package com.hms.concurrency.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.hms.concurrency.CheckInQueue;
import com.hms.concurrency.DoctorWorker;
import com.hms.domain.Appointment;
import com.hms.domain.AppointmentStatus;
import com.hms.domain.Doctor;
import com.hms.domain.Patient;
import com.hms.patterns.observer.AppointmentEventBus;

class DoctorWorkerTest {

	@Test
	void workerCompletesAppointment_andFiresEvent() throws Exception {
		CheckInQueue q = new CheckInQueue();
		AppointmentEventBus bus = new AppointmentEventBus();
		CountDownLatch fired = new CountDownLatch(1);
		bus.register(new com.hms.patterns.observer.AppointmentListener() {
			@Override
			public void onScheduled(Appointment a) {
			}

			@Override
			public void onCompleted(Appointment a) {
				fired.countDown();
			}
		});

		Thread worker = new Thread(new DoctorWorker(q, bus), "Doctor-Worker");
		worker.start();

		Appointment a = new Appointment.Builder().id("A-1").patient(new Patient("P", "Arun"))
				.doctor(new Doctor("D", "Meera")).slot(LocalDateTime.now().plusMinutes(1)).build();

		q.submit(a);

		assertTrue(fired.await(2, TimeUnit.SECONDS));
		assertEquals(AppointmentStatus.COMPLETED, a.getStatus());
		worker.interrupt();
		worker.join(1000);
	}
}
