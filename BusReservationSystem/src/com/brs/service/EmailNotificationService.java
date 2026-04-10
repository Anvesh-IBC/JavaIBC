package com.brs.service;

import com.brs.model.Booking;
import com.brs.model.Trip;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Simple FIFO queue to simulate notifications.
 */
public class EmailNotificationService implements NotificationService {
	private final Queue<String> queue = new ArrayDeque<>();

	@Override
	public void sendBookingConfirmed(Booking booking) {
		queue.add("EMAIL: Booking CONFIRMED: " + booking);
		drain();
	}

	@Override
	public void sendTripUpdate(Trip trip, String message) {
		queue.add("EMAIL: Trip Update " + trip.getId() + ": " + message);
		drain();
	}

	private void drain() {
		while (!queue.isEmpty()) {
			String msg = queue.poll();
			System.out.println("[NotificationQueue] " + msg);
		}
	}
}