package com.hms.dao;

import com.hms.model.Room;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRoomDAO implements RoomDAO {
	private final Map<Integer, Room> store = new ConcurrentHashMap<>();

	@Override
	public void save(Room room) {
		store.put(room.getRoomNumber(), room);
	}

	@Override
	public Optional<Room> findByNumber(int roomNumber) {
		return Optional.ofNullable(store.get(roomNumber));
	}

	@Override
	public List<Room> findAll() {
		return new ArrayList<>(store.values());
	}

	@Override
	public void update(Room room) {
		store.put(room.getRoomNumber(), room);
	}
}
