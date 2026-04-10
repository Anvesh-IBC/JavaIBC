package com.brs.model;

import com.brs.enums.BookingStatus;

import java.util.Objects;

public class Booking {
	private long id;
	private long tripId;
	private int seatIndex;
	private Passenger passenger;
	private BookingStatus status;

	public Booking() {
	}

	public Booking(long id, long tripId, int seatIndex, Passenger passenger, BookingStatus status) {
		this.id = id;
		this.tripId = tripId;
		this.seatIndex = seatIndex;
		this.passenger = passenger;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public long getTripId() {
		return tripId;
	}

	public int getSeatIndex() {
		return seatIndex;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Booking{id=" + id + ", tripId=" + tripId + ", seatIndex=" + seatIndex + ", passenger=" + passenger
				+ ", status=" + status + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Booking))
			return false;
		Booking booking = (Booking) o;
		return id == booking.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
