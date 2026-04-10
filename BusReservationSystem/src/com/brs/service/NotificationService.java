package com.brs.service;

import com.brs.model.Booking;
import com.brs.model.Trip;

public interface NotificationService {
	int MAX_RETRIES = 3; // interface constant

	void sendBookingConfirmed(Booking booking);

	void sendTripUpdate(Trip trip, String message);
}