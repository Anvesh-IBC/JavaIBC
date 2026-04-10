package com.hms.service;

import com.hms.dao.BookingDAO;
import com.hms.dao.RoomDAO;
import com.hms.model.*;
import com.hms.util.Ids;
import com.hms.util.PriceCalculator;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BookingService {

	private final BookingDAO bookingDAO;
	private final RoomDAO roomDAO;

	// Single lock to prevent double allocation (simple version)
	private final Object roomLock = new Object();

	public BookingService(BookingDAO bookingDAO, RoomDAO roomDAO) {
		this.bookingDAO = bookingDAO;
		this.roomDAO = roomDAO;
	}

	// =====================================================
	// CREATE BOOKING (ROOM -> BOOKED, BOOKING -> CREATED)
	// =====================================================
	public Booking create(Guest guest, RoomType type, LocalDate checkIn, LocalDate checkOut) {

		if (!checkOut.isAfter(checkIn)) {
			throw new IllegalArgumentException("Invalid dates");
		}

		Room allocated;

		synchronized (roomLock) {

			Optional<Room> opt = roomDAO.findAll().stream()
					.filter(r -> r.getType() == type && r.getStatus() == RoomStatus.AVAILABLE)
					.sorted(Comparator.comparingDouble(Room::getBasePrice)).findFirst();

			if (!opt.isPresent()) {
				throw new RuntimeException("No rooms available for type " + type);
			}

			allocated = opt.get();

			// IMPORTANT: mark room as BOOKED immediately
			allocated.setStatus(RoomStatus.BOOKED);
			roomDAO.update(allocated);
		}

		// Create booking
		String bookingId = Ids.bookingId();
		Booking booking = new Booking(bookingId, guest, allocated, checkIn, checkOut);

		// MISSING IN YOUR CODE (IMPORTANT)
		booking.setStatus(BookingStatus.CREATED);

		double nightly = PriceCalculator.computeNightly(allocated.getType(), allocated.getBasePrice());

		booking.setNightlyRate(nightly);

		double subtotal = nightly * booking.getNights();
		booking.setTax(PriceCalculator.computeTax(subtotal));
		booking.setDiscount(PriceCalculator.computeDiscount(booking.getNights()) * subtotal);

		bookingDAO.save(booking);
		return booking;
	}

	// =====================================================
	// CONFIRM (AFTER PAYMENT SUCCESS)
	// =====================================================
	public void confirm(String bookingId) {
		Booking b = load(bookingId);

		if (b.getStatus() != BookingStatus.CREATED) {
			throw new IllegalStateException("Only CREATED bookings can be confirmed");
		}

		b.setStatus(BookingStatus.CONFIRMED);
		bookingDAO.update(b);
	}

	// =====================================================
	// CHECK-IN
	// =====================================================
	public void checkIn(String bookingId) {
		Booking b = load(bookingId);

		if (b.getStatus() != BookingStatus.CONFIRMED) {
			throw new IllegalStateException("Only CONFIRMED bookings can check-in");
		}

		b.setStatus(BookingStatus.CHECKED_IN);
		bookingDAO.update(b);
	}

	// =====================================================
	// CHECK-OUT (RELEASE ROOM)
	// =====================================================
	public void checkOut(String bookingId) {
		Booking b = load(bookingId);

		if (b.getStatus() != BookingStatus.CHECKED_IN) {
			throw new IllegalStateException("Only CHECKED_IN bookings can check-out");
		}

		b.setStatus(BookingStatus.CHECKED_OUT);
		bookingDAO.update(b);

		releaseRoom(b.getRoom());
	}

	// =====================================================
	// CANCEL (PAYMENT FAILED)
	// =====================================================
	public void cancel(String bookingId) {
		Booking b = load(bookingId);

		if (b.getStatus() == BookingStatus.CHECKED_OUT) {
			throw new IllegalStateException("Already checked out");
		}

		b.setStatus(BookingStatus.CANCELLED);
		bookingDAO.update(b);

		releaseRoom(b.getRoom());
	}

	// =====================================================
	// ROOM RELEASE (SAFE)
	// =====================================================
	private void releaseRoom(Room room) {
		synchronized (roomLock) {
			room.setStatus(RoomStatus.AVAILABLE);
			roomDAO.update(room);
		}
	}

	// =====================================================
	// LOAD / LIST
	// =====================================================
	public Booking load(String bookingId) {
		return bookingDAO.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
	}

	public List<Booking> listAll() {
		return bookingDAO.findAll();
	}
}
