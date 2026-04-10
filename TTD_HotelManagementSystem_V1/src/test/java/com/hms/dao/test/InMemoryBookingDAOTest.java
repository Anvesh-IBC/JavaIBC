package com.hms.dao.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.hms.dao.InMemoryBookingDAO;
import com.hms.model.Booking;
import com.hms.model.BookingStatus;
import com.hms.model.Guest;
import com.hms.model.Room;
import com.hms.model.RoomType;

class InMemoryBookingDAOTest {

	@Test
	void save_findById_findByGuest_findAll_update() {
		InMemoryBookingDAO dao = new InMemoryBookingDAO();

		Guest g1 = new Guest("G-1", "N1", "9876543210", "a@a.com");
		Guest g2 = new Guest("G-2", "N2", "9876543210", "b@b.com");
		Room r = new Room(101, RoomType.STANDARD, 1);

		Booking b1 = new Booking("BK-1", g1, r, LocalDate.now(), LocalDate.now().plusDays(1));
		Booking b2 = new Booking("BK-2", g2, r, LocalDate.now(), LocalDate.now().plusDays(2));
		dao.save(b1);
		dao.save(b2);

		assertThat(dao.findById("BK-1")).contains(b1);
		assertThat(dao.findByGuest("G-1")).containsExactly(b1);
		assertThat(dao.findAll()).hasSize(2);

		b1.setStatus(BookingStatus.CONFIRMED);
		dao.update(b1);
		assertThat(dao.findById("BK-1")).get().extracting(Booking::getStatus).isEqualTo(BookingStatus.CONFIRMED);
	}
}
