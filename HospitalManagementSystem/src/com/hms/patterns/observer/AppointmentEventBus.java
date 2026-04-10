package com.hms.patterns.observer;

import java.util.*;

import com.hms.domain.Appointment;

public class AppointmentEventBus {
	private final List<AppointmentListener> listeners = new ArrayList<>();

	public void register(AppointmentListener l) {
		listeners.add(l);
	}

	public void fireScheduled(Appointment a) {
		for (AppointmentListener l : listeners)
			l.onScheduled(a);
	}

	public void fireCompleted(Appointment a) {
		for (AppointmentListener l : listeners)
			l.onCompleted(a);
	}
}
