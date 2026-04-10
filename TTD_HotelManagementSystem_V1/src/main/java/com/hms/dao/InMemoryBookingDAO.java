package com.hms.dao;

import com.hms.model.Booking;
import java.util.*;

public class InMemoryBookingDAO implements BookingDAO {
	private final Map<String, Booking> store = new HashMap<>();

	@Override
	public void save(Booking booking) {
		store.put(booking.getBookingId(), booking);
	}

	@Override
	public Optional<Booking> findById(String bookingId) {
		return Optional.ofNullable(store.get(bookingId));
	}

	@Override
	public List<Booking> findByGuest(String guestId) {
		List<Booking> list = new ArrayList<>();
		for (Booking b : store.values()) {
			if (b.getGuest() != null && guestId.equals(b.getGuest().getGuestId()))
				list.add(b);
		}
		return list;
	}

	@Override
	public List<Booking> findAll() {
		return new ArrayList<>(store.values());
	}

	@Override
	public void update(Booking booking) {
		store.put(booking.getBookingId(), booking);
	}
}
