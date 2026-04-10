package com.hms.dao;

import com.hms.model.Booking;
import java.util.List;
import java.util.Optional;

public interface BookingDAO {
	void save(Booking booking);

	Optional<Booking> findById(String bookingId);

	List<Booking> findByGuest(String guestId);

	List<Booking> findAll();

	void update(Booking booking);
}
