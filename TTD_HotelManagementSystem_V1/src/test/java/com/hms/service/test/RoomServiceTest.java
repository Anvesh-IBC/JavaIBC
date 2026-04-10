package com.hms.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hms.dao.RoomDAO;
import com.hms.model.Room;
import com.hms.model.RoomStatus;
import com.hms.model.RoomType;
import com.hms.service.RoomService;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

	@Mock
	private RoomDAO roomDAO;
	@InjectMocks
	private RoomService service;

	@Test
	void add_find_list_delegateToDAO() {
		Room r = new Room(101, RoomType.STANDARD, 1);
		service.addRoom(r);
		verify(roomDAO).save(r);

		when(roomDAO.findByNumber(101)).thenReturn(Optional.of(r));
		assertThat(service.findByNumber(101)).contains(r);

		when(roomDAO.findAll()).thenReturn(Collections.singletonList(r));
		assertThat(service.listAll()).containsExactly(r);
	}

	@Test
	void markMaintenance_updatesRoom() {
		Room r = new Room(202, RoomType.DELUXE, 2);
		when(roomDAO.findByNumber(202)).thenReturn(Optional.of(r));
		service.markMaintenance(202);
		assertThat(r.getStatus()).isEqualTo(RoomStatus.MAINTENANCE);
		verify(roomDAO).update(r);
	}

	@Test
	void findAvailable_filtersByTypeAndStatus_andSortsByPrice() {
		Room a = new Room(101, RoomType.SUITE, 3);
		a.setStatus(RoomStatus.AVAILABLE);
		Room b = new Room(102, RoomType.SUITE, 3);
		b.setStatus(RoomStatus.AVAILABLE);
		b.setBasePrice(a.getBasePrice() + 500);
		Room c = new Room(201, RoomType.DELUXE, 2);
		c.setStatus(RoomStatus.AVAILABLE);
		Room d = new Room(301, RoomType.SUITE, 3);
		d.setStatus(RoomStatus.BOOKED);

		when(roomDAO.findAll()).thenReturn(Arrays.asList(a, b, c, d));
		List<Room> res = service.findAvailable(RoomType.SUITE, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 3));

		assertThat(res).containsExactly(a, b); // sorted by basePrice ascending
	}
}
