package com.hms.model;

import java.util.Objects;

public class Room {

	private final int roomNumber;
	private final RoomType type;
	private final int floor;
	private final RoomStatus status;
	private final double basePrice;

	// Default base price logic
	private static double defaultBasePrice(RoomType type) {
		switch (type) {
		case DELUXE:
			return 4000.0;
		case SUITE:
			return 5000.0;
		case STANDARD:
		default:
			return 3000.0;
		}
	}

	// Existing constructor (used when adding rooms)
	public Room(int roomNumber, RoomType type, int floor) {
		this(roomNumber, type, floor, RoomStatus.AVAILABLE);
	}

	// NEW CONSTRUCTOR 
	public Room(int roomNumber, RoomType type, int floor, RoomStatus status) {
		this.roomNumber = roomNumber;
		this.type = type;
		this.floor = floor;
		this.status = status;
		this.basePrice = defaultBasePrice(type);
	}

	// ================= Getters =================

	public int getRoomNumber() {
		return roomNumber;
	}

	public RoomType getType() {
		return type;
	}

	public int getFloor() {
		return floor;
	}

	public RoomStatus getStatus() {
		return status;
	}

	public double getBasePrice() {
		return basePrice;
	}

	// ================= Object methods =================

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Room))
			return false;
		Room room = (Room) o;
		return roomNumber == room.roomNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(roomNumber);
	}

	@Override
	public String toString() {
		return "Room{" + "no=" + roomNumber + ", type=" + type + ", status=" + status + ", basePrice=" + basePrice
				+ ", floor=" + floor + '}';
	}
}
