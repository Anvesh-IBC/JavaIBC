package com.hms.test;

import com.hms.dao.*;
import com.hms.model.*;
import com.hms.service.*;
import com.hms.event.EventBus;
import com.hms.util.PriceCalculator;

import java.time.LocalDate;
import java.util.concurrent.*;

public class ConcurrencyTest {

	public static void main(String[] args) throws Exception {

		// ---- Setup DAOs ----
		RoomDAO roomDAO = new InMemoryRoomDAO();
		BookingDAO bookingDAO = new InMemoryBookingDAO();

		// ---- Add ONE room only ----
		Room room = new Room(101, RoomType.DELUXE, 1);
		roomDAO.save(room);

		// ---- Infrastructure ----
		RoomLockRegistry lockRegistry = new RoomLockRegistry();
		EventBus eventBus = new EventBus();

		// ---- Pricing init (REQUIRED in Version-2) ----
		RatesCatalog ratesCatalog = new RatesCatalog();
		PriceCalculator.init(ratesCatalog);

		BookingService bookingService = new BookingService(bookingDAO, roomDAO, lockRegistry, eventBus);

		ExecutorService pool = Executors.newFixedThreadPool(6);

		CountDownLatch start = new CountDownLatch(1);
		CountDownLatch done = new CountDownLatch(20);

		// ---- Test dates ----
		LocalDate checkIn = LocalDate.now().plusDays(1);
		LocalDate checkOut = checkIn.plusDays(2);

		for (int i = 0; i < 20; i++) {
			final int idx = i;

			pool.submit(() -> {
				try {
					start.await(); // all threads start together

					Guest guest = new Guest("G-" + idx, "Guest-" + idx, "9000000" + idx, "guest" + idx + "@mail.com");

					bookingService.create(guest, RoomType.DELUXE, checkIn, checkOut);

					System.out.println("SUCCESS: Guest " + idx);

				} catch (Exception e) {
					System.out.println("FAILED : Guest " + idx + " -> " + e.getMessage());
				} finally {
					done.countDown();
				}
			});
		}

		System.out.println("Releasing all threads...");
		start.countDown();

		done.await();
		pool.shutdown();

		System.out.println("\nTotal bookings created: " + bookingDAO.findAll().size());
	}
}
