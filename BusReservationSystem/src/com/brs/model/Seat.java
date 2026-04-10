package com.brs.model;

import java.util.Objects;

/** Seat with index and reservation state */

public class Seat {
	private final int index;
	private boolean reserved; // demonstrates boolean flag
	private Long bookingId; // link to Booking (avoid circular reference)

	public Seat(int index) {
		if (index < 0)
			throw new IllegalArgumentException("Seat index must be >= 0.");
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public boolean isReserved() {
		return reserved;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void reserve(long bookingId) {
		this.reserved = true;
		this.bookingId = bookingId;
	}

	public void clear() {
		this.reserved = false;
		this.bookingId = null;
	}

	@Override
	public String toString() {
		return "Seat{index=" + index + ", reserved=" + reserved + ", bookingId=" + bookingId + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Seat))
			return false;
		Seat seat = (Seat) o;
		return index == seat.index;
	}

	@Override
	public int hashCode() {
		return Objects.hash(index);
	}
}
