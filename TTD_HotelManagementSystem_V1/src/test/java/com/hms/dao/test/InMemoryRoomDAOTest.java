package com.hms.dao.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.hms.dao.InMemoryRoomDAO;
import com.hms.model.Room;
import com.hms.model.RoomStatus;
import com.hms.model.RoomType;

class InMemoryRoomDAOTest {

	@Test
	void save_find_update_and_list() {
		InMemoryRoomDAO dao = new InMemoryRoomDAO();
		Room r = new Room(101, RoomType.STANDARD, 1);
		dao.save(r);

		assertThat(dao.findByNumber(101)).contains(r);
		assertThat(dao.findAll()).hasSize(1);

		r.setStatus(RoomStatus.BOOKED);
		dao.update(r);
		assertThat(dao.findByNumber(101)).get().extracting(Room::getStatus).isEqualTo(RoomStatus.BOOKED);
	}
}
