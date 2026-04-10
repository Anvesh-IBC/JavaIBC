package com.brs.repo;

import com.brs.model.Booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingRepository {
	private final Map<Long, Booking> byId = new HashMap<Long, Booking>();

	public void add(Booking b) {
		if (b == null) {
			throw new IllegalArgumentException("Booking cannot be null.");
		}
		byId.put(b.getId(), b);
	}

	/**
	 * Java 7: Returns Booking or null instead of Optional.
	 */
	public Booking find(long id) {
		return byId.get(id);
	}

	/**
	 * Returns all bookings as a new List.
	 */
	public List<Booking> list() {
		return new ArrayList<Booking>(byId.values());
	}
}