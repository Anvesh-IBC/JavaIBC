package com.hms.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hms.dao.BookingDAO;
import com.hms.dao.RoomDAO;
import com.hms.model.Booking;
import com.hms.model.BookingStatus;
import com.hms.model.Guest;
import com.hms.model.Room;
import com.hms.model.RoomStatus;
import com.hms.model.RoomType;
import com.hms.service.BookingService;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

	@Mock
	private BookingDAO bookingDAO;
	@Mock
	private RoomDAO roomDAO;

	@InjectMocks
	private BookingService service;

	private final Guest guest = new Guest("G-1", "Prasanna", "9876543210", "p@e.com");

	@Test
	void create_shouldPickCheapestAvailableRoom_setBooked_calculatePrices_andSave() {
		Room r1 = new Room(201, RoomType.DELUXE, 2); // base default 3000 +1000 in ctor => 4000
		Room r2 = new Room(202, RoomType.DELUXE, 2); // same type, but make slightly pricier
		r2.setBasePrice(4500); // becomes 4500 (already uplifted in ctor)

		// r1 available, r2 available
		r1.setStatus(RoomStatus.AVAILABLE);
		r2.setStatus(RoomStatus.AVAILABLE);

		when(roomDAO.findAll()).thenReturn(Arrays.asList(r1, r2));

		LocalDate in = LocalDate.of(2024, 8, 1);
		LocalDate out = LocalDate.of(2024, 8, 4); // 3 nights

		ArgumentCaptor<Booking> saved = ArgumentCaptor.forClass(Booking.class);

		Booking created = service.create(guest, RoomType.DELUXE, in, out);

		// Verify room selection & immediate BOOKED update
		assertEquals(RoomStatus.BOOKED, r1.getStatus(), "Cheapest room must be booked immediately");
		verify(roomDAO, times(1)).update(r1);

		// Verify booking details saved
		verify(bookingDAO).save(saved.capture());
		Booking b = saved.getValue();
		assertEquals(created.getBookingId(), b.getBookingId());
		assertEquals(guest, b.getGuest());
		assertEquals(r1, b.getRoom());
		assertEquals(3, b.getNights());
		assertEquals(BookingStatus.CREATED, b.getStatus());

		// PriceCalculator.computeNightly(roomType, basePrice)
		// r1 basePrice is 4000 after ctor; nightly = base + uplift(DELUXE=1000) => 5000
		assertEquals(5000d, b.getNightlyRate(), 0.0001);

		double subtotal = 5000 * 3; // 15000
		double tax = 0.12 * subtotal; // 1800
		double discount = 0.0; // <7 nights
		assertEquals(tax, b.getTax(), 0.0001);
		assertEquals(discount, b.getDiscount(), 0.0001);

		assertEquals(subtotal + tax - discount, b.totalAmount(), 0.0001);
	}

	@Test
	void create_invalidDates_shouldThrow() {
		LocalDate in = LocalDate.of(2024, 8, 10);
		LocalDate out = LocalDate.of(2024, 8, 9);
		assertThrows(IllegalArgumentException.class, () -> service.create(guest, RoomType.STANDARD, in, out));
		verifyNoInteractions(bookingDAO);
	}

	@Test
	void create_noRoomsAvailable_shouldThrow() {
		when(roomDAO.findAll()).thenReturn(Collections.singletonList(new Room(101, RoomType.STANDARD, 1) {
			{
				setStatus(RoomStatus.MAINTENANCE);
			}
		}));
		LocalDate in = LocalDate.of(2024, 8, 1);
		LocalDate out = LocalDate.of(2024, 8, 2);
		RuntimeException ex = assertThrows(RuntimeException.class,
				() -> service.create(guest, RoomType.STANDARD, in, out));
		assertTrue(ex.getMessage().contains("No rooms available"));
		verifyNoInteractions(bookingDAO);
	}

	@Test
	void confirm_onlyCreated_canBeConfirmed() {
		Booking b = baseBooking(BookingStatus.CREATED);
		when(bookingDAO.findById("BK-1")).thenReturn(Optional.of(b));
		service.confirm("BK-1");
		assertEquals(BookingStatus.CONFIRMED, b.getStatus());
		verify(bookingDAO).update(b);
	}

	@Test
	void confirm_wrongState_shouldThrow() {
		Booking b = baseBooking(BookingStatus.CHECKED_IN);
		when(bookingDAO.findById("BK-1")).thenReturn(Optional.of(b));
		assertThrows(IllegalStateException.class, () -> service.confirm("BK-1"));
		verify(bookingDAO, never()).update(any());
	}

	@Test
	void checkIn_onlyConfirmed_canCheckIn() {
		Booking b = baseBooking(BookingStatus.CONFIRMED);
		when(bookingDAO.findById("BK-1")).thenReturn(Optional.of(b));
		service.checkIn("BK-1");
		assertEquals(BookingStatus.CHECKED_IN, b.getStatus());
		verify(bookingDAO).update(b);
	}

	@Test
	void checkOut_onlyCheckedIn_canCheckOut_andReleasesRoom() {
		Room r = new Room(301, RoomType.SUITE, 3);
		r.setStatus(RoomStatus.BOOKED);
		Booking b = new Booking("BK-1", guest, r, LocalDate.now(), LocalDate.now().plusDays(1));
		b.setStatus(BookingStatus.CHECKED_IN);
		when(bookingDAO.findById("BK-1")).thenReturn(Optional.of(b));

		service.checkOut("BK-1");

		assertEquals(BookingStatus.CHECKED_OUT, b.getStatus());
		verify(bookingDAO).update(b);
		// room release -> AVAILABLE and update(roomDAO)
		assertEquals(RoomStatus.AVAILABLE, r.getStatus());
		verify(roomDAO).update(r);
	}

	@Test
	void cancel_setsCancelled_andReleasesRoom_unlessAlreadyCheckedOut() {
		Room r = new Room(101, RoomType.STANDARD, 1);
		r.setStatus(RoomStatus.BOOKED);
		Booking b = new Booking("BK-1", guest, r, LocalDate.now(), LocalDate.now().plusDays(2));
		b.setStatus(BookingStatus.CONFIRMED);
		when(bookingDAO.findById("BK-1")).thenReturn(Optional.of(b));

		service.cancel("BK-1");

		assertEquals(BookingStatus.CANCELLED, b.getStatus());
		verify(bookingDAO).update(b);
		assertEquals(RoomStatus.AVAILABLE, r.getStatus());
		verify(roomDAO).update(r);
		
	}

	@Test
	void cancel_checkedOut_shouldThrow() {
		Booking b = baseBooking(BookingStatus.CHECKED_OUT);
		when(bookingDAO.findById("BK-1")).thenReturn(Optional.of(b));
		assertThrows(IllegalStateException.class, () -> service.cancel("BK-1"));
		verify(bookingDAO, never()).update(any());
	}

	@Test
	void load_and_listAll_proxyToDAO() {
		Booking bk = baseBooking(BookingStatus.CREATED);
		when(bookingDAO.findById("BK-1")).thenReturn(Optional.of(bk));
		when(bookingDAO.findAll()).thenReturn(Collections.singletonList(bk));

		assertSame(bk, service.load("BK-1"));
		assertThat(service.listAll()).containsExactly(bk);
	}

	private Booking baseBooking(BookingStatus status) {
		Room r = new Room(101, RoomType.STANDARD, 1);
		Booking b = new Booking("BK-1", guest, r, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 3));
		b.setStatus(status);
		return b;
	}
}
