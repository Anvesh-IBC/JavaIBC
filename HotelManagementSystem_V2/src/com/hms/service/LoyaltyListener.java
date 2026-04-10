package com.hms.service;

import com.hms.event.BookingConfirmed;
import com.hms.event.DomainEvent;

public class LoyaltyListener {

	public void onEvent(DomainEvent event) {
		if (event instanceof BookingConfirmed) {
			BookingConfirmed bc = (BookingConfirmed) event;

			System.out.println("Loyalty points added for booking " + bc.getBookingId());
		}
	}
}
