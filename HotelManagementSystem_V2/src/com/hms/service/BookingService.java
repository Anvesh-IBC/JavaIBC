package com.hms.service;

import com.hms.dao.BookingDAO;
import com.hms.dao.RoomDAO;
import com.hms.event.BookingConfirmed;
import com.hms.event.EventBus;
import com.hms.model.*;
import com.hms.util.Ids;
import com.hms.util.PriceCalculator;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class BookingService {

	private final BookingDAO bookingDAO;
	private final RoomDAO roomDAO;
	private final RoomLockRegistry roomLockRegistry;
	private final EventBus eventBus;

	public BookingService(BookingDAO bookingDAO, RoomDAO roomDAO, RoomLockRegistry roomLockRegistry,
			EventBus eventBus) {

		this.bookingDAO = bookingDAO;
		this.roomDAO = roomDAO;
		this.roomLockRegistry = roomLockRegistry;
		this.eventBus = eventBus;
	}
	
	// ================= ROOM LIST =================
	
	public Optional<Booking> findActiveBookingForRoom(int roomNumber) {
	    return bookingDAO.findAll().stream()
	            .filter(b -> b.getRoom().getRoomNumber() == roomNumber)
	            .filter(b -> b.getStatus() != BookingStatus.CHECKED_OUT
	                      && b.getStatus() != BookingStatus.CANCELLED)
	            .findFirst();
	}

	// ================= CREATE + AUTO CONFIRM =================

	public Booking create(Guest guest, RoomType type, LocalDate checkIn, LocalDate checkOut) {

		if (!checkOut.isAfter(checkIn)) {
			throw new IllegalArgumentException("Invalid dates");
		}

		// 1 Find available room (NO LOCK)
		Optional<Room> opt = roomDAO.findAll().stream()
				.filter(r -> r.getType() == type && r.getStatus() == RoomStatus.AVAILABLE)
				.sorted(Comparator.comparingDouble(Room::getBasePrice)).findFirst();

		if (!opt.isPresent()) {
			throw new RuntimeException("No rooms available for type " + type);
		}

		Room candidate = opt.get();

		// 2 Lock ONLY selected room
		ReentrantLock lock = roomLockRegistry.getLock(candidate.getRoomNumber());
		lock.lock();

		Room bookedRoom;
		try {
			Room fresh = roomDAO.findByNumber(candidate.getRoomNumber())
					.orElseThrow(() -> new RuntimeException("Room not found"));

			if (fresh.getStatus() != RoomStatus.AVAILABLE) {
				throw new RuntimeException("Room already booked");
			}

			// Mark room as BOOKED (immutable replacement)
			bookedRoom = new Room(fresh.getRoomNumber(), fresh.getType(), fresh.getFloor(), RoomStatus.BOOKED);

			roomDAO.update(bookedRoom);

		} finally {
			lock.unlock();
		}

		// 3 Create booking
		String id = Ids.bookingId();
		Booking booking = new Booking(id, guest, bookedRoom, checkIn, checkOut);

		double nightly = PriceCalculator.computeNightly(bookedRoom.getType(), checkIn);

		booking.setNightlyRate(nightly);

		double subtotal = nightly * booking.getNights();
		booking.setTax(PriceCalculator.computeTax(subtotal));
		booking.setDiscount(PriceCalculator.computeDiscount(booking.getNights()) * subtotal);

		// 4 AUTO CONFIRM (KEY CHANGE)
		booking.setStatus(BookingStatus.CONFIRMED);

		bookingDAO.save(booking);

		// 5 Publish event
		eventBus.publish(new BookingConfirmed(booking.getBookingId()));

		return booking;
	}

	// ================= LIFECYCLE =================

	public void confirm(String bookingId) {
		Booking b = load(bookingId);
		if (b.getStatus() != BookingStatus.CREATED) {
			throw new IllegalStateException("Only CREATED bookings can be confirmed");
		}
		b.setStatus(BookingStatus.CONFIRMED);
		bookingDAO.update(b);
	}

	public void checkIn(String bookingId) {
		Booking b = load(bookingId);
		if (b.getStatus() != BookingStatus.CONFIRMED) {
			throw new IllegalStateException("Only CONFIRMED bookings can check-in");
		}
		b.setStatus(BookingStatus.CHECKED_IN);
		bookingDAO.update(b);
	}

	public void checkOut(String bookingId) {
		Booking b = load(bookingId);
		if (b.getStatus() != BookingStatus.CHECKED_IN) {
			throw new IllegalStateException("Only CHECKED_IN bookings can check-out");
		}

		b.setStatus(BookingStatus.CHECKED_OUT);
		bookingDAO.update(b);

		releaseRoom(b.getRoom());
	}

	public void cancel(String bookingId) {
		Booking b = load(bookingId);
		if (b.getStatus() == BookingStatus.CHECKED_OUT) {
			throw new IllegalStateException("Already checked out");
		}

		b.setStatus(BookingStatus.CANCELLED);
		bookingDAO.update(b);

		releaseRoom(b.getRoom());
	}

	// ================= ROOM RELEASE =================

	private void releaseRoom(Room room) {
		ReentrantLock lock = roomLockRegistry.getLock(room.getRoomNumber());
		lock.lock();
		try {
			Room availableRoom = new Room(room.getRoomNumber(), room.getType(), room.getFloor(), RoomStatus.AVAILABLE);
			roomDAO.update(availableRoom);
		} finally {
			lock.unlock();
		}
	}

	// ================= QUERIES =================

	public Booking load(String bookingId) {
		return bookingDAO.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
	}

	public List<Booking> listAll() {
		return bookingDAO.findAll();
	}
}
