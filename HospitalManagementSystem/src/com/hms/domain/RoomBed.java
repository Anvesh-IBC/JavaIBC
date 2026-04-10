package com.hms.domain;

public class RoomBed {
	private final String roomNo;
	private final String bedNo;
	private boolean occupied;

	public RoomBed(String roomNo, String bedNo) {
		this.roomNo = roomNo;
		this.bedNo = bedNo;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void occupy() {
		if (occupied)
			throw new IllegalStateException("Already occupied");
		occupied = true;
	}

	public void free() {
		occupied = false;
	}

	@Override
	public String toString() {
		return roomNo + "-" + bedNo;
	}
}
