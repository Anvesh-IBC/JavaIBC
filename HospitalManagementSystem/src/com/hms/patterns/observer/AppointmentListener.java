package com.hms.patterns.observer;

import com.hms.domain.Appointment;

public interface AppointmentListener {
	void onScheduled(Appointment a);

	void onCompleted(Appointment a);
}
