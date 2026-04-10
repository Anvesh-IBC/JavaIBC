package com.hms.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class RoomLockRegistry {

	private final ConcurrentHashMap<Integer, ReentrantLock> locks = new ConcurrentHashMap<>();

	public ReentrantLock getLock(int roomNumber) {
		return locks.computeIfAbsent(roomNumber, rn -> new ReentrantLock());
	}
}
