package com.hms.event;

public class BookingConfirmed implements DomainEvent {

	private final String bookingId;

	public BookingConfirmed(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getBookingId() {
		return bookingId;
	}
}
