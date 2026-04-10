package com.brs.service;

import com.brs.util.Util;
import com.brs.enums.BookingStatus;
import com.brs.enums.PaymentMethod;
import com.brs.enums.PaymentStatus;
import com.brs.model.Booking;
import com.brs.model.Passenger;
import com.brs.model.Payment;
import com.brs.model.Seat;
import com.brs.model.Trip;
import com.brs.repo.BookingRepository;
import com.brs.repo.PaymentRepository;
import com.brs.repo.TripRepository;
import com.brs.service.NotificationService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class ReservationService {
	private final FareService fareService;
	private final TripRepository tripRepo;
	private final BookingRepository bookingRepo;
	private final PaymentRepository paymentRepo;
	private final NotificationService notifier;

	private final AtomicLong bookingSeq = new AtomicLong(1000);
	private final AtomicLong paymentSeq = new AtomicLong(5000);

	public ReservationService(FareService fareService, TripRepository tripRepo, BookingRepository bookingRepo,
			PaymentRepository paymentRepo, NotificationService notifier) {
		this.fareService = fareService;
		this.tripRepo = tripRepo;
		this.bookingRepo = bookingRepo;
		this.paymentRepo = paymentRepo;
		this.notifier = notifier;
	}

	public Booking bookSeat(long tripId, int seatIndex, Passenger passenger, PaymentMethod method) {
		Trip trip = tripRepo.find(tripId);
		if (trip == null) {
			throw new IllegalArgumentException("Trip not found: " + tripId);
		}
		if (!trip.isActive()) {
			throw new IllegalStateException("Trip is not active.");
		}

		Seat seat = trip.getSeat(seatIndex);

		// Logical/relational operators
		boolean available = !seat.isReserved();
		if (!available) {
			throw new IllegalStateException("Seat already reserved.");
		}

		// REQUESTED -> CONFIRMED (Phase 1: assume payment success)
		long bookingId = bookingSeq.incrementAndGet();
		Booking booking = new Booking(bookingId, tripId, seatIndex, passenger, BookingStatus.REQUESTED);

		// Compute fare using FareService (operators & enums)
		double totalFare = fareService.totalFare(trip.getRoute(), trip.getServiceType());

		long paymentId = paymentSeq.incrementAndGet();
		// Payment payment = new Payment(paymentId, bookingId, totalFare, "INR", method,
		// PaymentStatus.INITIATED, new Date());
		Payment payment = new Payment(paymentId, bookingId, totalFare, "INR", method, PaymentStatus.INITIATED,
				LocalDateTime.now());

		// Simulated payment success
		boolean paymentSuccess = true;
		payment.setStatus(paymentSuccess ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);

		// Ternary usage
		BookingStatus finalStatus = (available && paymentSuccess) ? BookingStatus.CONFIRMED : BookingStatus.WAITLISTED;
		booking.setStatus(finalStatus);

		if (finalStatus == BookingStatus.CONFIRMED) {
			seat.reserve(bookingId);
		}

		bookingRepo.add(booking);
		paymentRepo.add(payment);

		notifier.sendBookingConfirmed(booking);
		System.out.println(buildTicket(booking, trip, payment));

		return booking;
	}

	public boolean cancel(long bookingId) {
		Booking booking = bookingRepo.find(bookingId);
		if (booking == null) {
			throw new IllegalArgumentException("Booking not found.");
		}
		Trip trip = tripRepo.find(booking.getTripId());
		if (trip == null) {
			throw new IllegalArgumentException("Trip not found.");
		}

		if (booking.getStatus() == BookingStatus.CANCELLED) {
			return false;
		}
		booking.setStatus(BookingStatus.CANCELLED);
		trip.getSeat(booking.getSeatIndex()).clear();
		notifier.sendTripUpdate(trip, "Booking " + bookingId + " cancelled; seat freed.");
		return true;
	}

	/** Demonstrates StringBuilder usage for formatted multi-line ticket */
	private String buildTicket(Booking booking, Trip trip, Payment payment) {
		StringBuilder sb = new StringBuilder(128);
		sb.append("\n----------- BRS TICKET -----------\n");
		sb.append("Booking ID   : ").append(booking.getId()).append('\n');
		sb.append("Passenger    : ").append(booking.getPassenger().getName()).append(" (")
				.append(booking.getPassenger().getPhone()).append(")\n");
		sb.append("Route        : ").append(trip.getRoute().getOrigin()).append(" -> ")
				.append(trip.getRoute().getDestination()).append('\n');
		// sb.append("Schedule : ").append(Util.formatSchedule(trip.getDate(),
		// trip.getDeparture(), trip.getArrival())).append('\n');

		LocalDate date = trip.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalTime dep = LocalTime.parse(trip.getDeparture());
		LocalTime arr = LocalTime.parse(trip.getArrival());
		sb.append("Schedule     : ").append(Util.formatSchedule(date, dep, arr)).append('\n');

		sb.append("Bus          : ").append(trip.getBus().getPlateNumber()).append(" [").append(trip.getServiceType())
				.append("]\n");
		sb.append("Seat Index   : ").append(booking.getSeatIndex()).append('\n');
		sb.append("Status       : ").append(booking.getStatus()).append('\n');
		sb.append("Fare (Total) : ").append(Util.formatCurrency(payment.getAmount())).append('\n');
		sb.append("Payment      : ").append(payment.getMethod()).append(' ').append(payment.getStatus()).append('\n');
		sb.append("----------------------------------\n");
		return sb.toString();
	}
}