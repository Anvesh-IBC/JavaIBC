package com.hms.dao;

import com.hms.model.Room;
import java.util.List;
import java.util.Optional;

public interface RoomDAO {
	void save(Room room);

	Optional<Room> findByNumber(int roomNumber);

	List<Room> findAll();

	void update(Room room);
}
