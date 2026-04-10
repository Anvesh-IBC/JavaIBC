package com.hms.service;

import java.util.*;

import com.hms.domain.RoomBed;

public class BedService {
	private final List<RoomBed> beds = new ArrayList<>();

	public BedService(List<RoomBed> initial) {
		beds.addAll(initial);
	}

	public synchronized RoomBed allocate() throws InterruptedException {
		while (true) {
			for (RoomBed b : beds) {
				if (!b.isOccupied()) {
					b.occupy();
					return b;
				}
			}
			wait(); // wait until a bed is freed
		}
	}

	public synchronized void free(RoomBed b) {
		b.free();
		notifyAll();
	} // wake all waiting threads
}
