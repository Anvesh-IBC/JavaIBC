package com.hms.model;

import java.util.Objects;

public class Room {
	private int roomNumber;
	private double basePrice;
	private RoomType type;
	private RoomStatus status = RoomStatus.AVAILABLE;
	private int floor;

	{ // initialization block
		basePrice = 3000.0; // default base rate
	}

	public Room() {
	}

	public Room(int roomNumber, RoomType type, int floor) {
		this.roomNumber = roomNumber;
		this.type = type;
		this.floor = floor;
		// price uplift by type
		if (type == RoomType.DELUXE)
			basePrice += 1000;
		else if (type == RoomType.SUITE)
			basePrice += 2000;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public RoomType getType() {
		return type;
	}

	public void setType(RoomType type) {
		this.type = type;
	}

	public RoomStatus getStatus() {
		return status;
	}

	public void setStatus(RoomStatus status) {
		this.status = status;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

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
