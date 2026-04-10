package com.hms.concurrency;

import com.hms.domain.*;
import com.hms.patterns.observer.AppointmentEventBus;

public class DoctorWorker implements Runnable {
	private final CheckInQueue q;
	private final AppointmentEventBus bus;

	public DoctorWorker(CheckInQueue q, AppointmentEventBus bus) {
		this.q = q;
		this.bus = bus;
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			Appointment a = q.take();
			if (a == null)
				continue;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			a.setStatus(AppointmentStatus.COMPLETED);
			bus.fireCompleted(a);
		}
	}
}
