package com.hms.service;

import com.hms.dao.RoomDAO;
import com.hms.model.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RoomService {
	private final RoomDAO roomDAO;

	public RoomService(RoomDAO roomDAO) {
		this.roomDAO = roomDAO;
	}

	public void addRoom(Room room) {
		roomDAO.save(room);
	}

	public Optional<Room> findByNumber(int roomNumber) {
		return roomDAO.findByNumber(roomNumber);
	}

	public List<Room> listAll() {
		return roomDAO.findAll();
	}

	public void markMaintenance(int roomNumber) {
		Room r = roomDAO.findByNumber(roomNumber).orElseThrow(() -> new RuntimeException("Room not found"));
		r.setStatus(RoomStatus.MAINTENANCE);
		roomDAO.update(r);
	}

	// For now, we don't keep a date-calendar; we filter only by availability and
	// type.
	public List<Room> findAvailable(RoomType type, LocalDate from, LocalDate to) {
		return roomDAO.findAll().stream().filter(r -> r.getType() == type && r.getStatus() == RoomStatus.AVAILABLE)
				.sorted(Comparator.comparingDouble(Room::getBasePrice)).collect(Collectors.toList());
	}
}
